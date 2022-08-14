<template>
  <div :class="classObj" class="app-wrapper" :style="{'--current-color': theme}">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside"/>
    <sidebar v-if="!sidebar.hide" class="sidebar-container" />
    <div :class="{hasTagsView:needTagsView,sidebarHide:sidebar.hide}" class="main-container">
      <div :class="{'fixed-header':fixedHeader}">
        <navbar />
        <tags-view v-if="needTagsView" />
      </div>
      <app-main />
      <right-panel>
        <settings />
      </right-panel>
    </div>
  </div>
</template>

<script>
import RightPanel from '@/components/RightPanel'
import { AppMain, Navbar, Settings, Sidebar, TagsView } from './components'
import ResizeMixin from './mixin/ResizeHandler'
import { mapState } from 'vuex'
import variables from '@/assets/styles/variables.scss'
import {addBackupLog, getTableData, listBackupLog} from "../api/backupLog";
import moment from "moment";
import {getDicts} from "../api/system/dict/data";
import XLSX from 'xlsx';

export default {
  name: 'Layout',
  components: {
    AppMain,
    Navbar,
    RightPanel,
    Settings,
    Sidebar,
    TagsView
  },
  mixins: [ResizeMixin],
  computed: {
    ...mapState({
      theme: state => state.settings.theme,
      sideTheme: state => state.settings.sideTheme,
      sidebar: state => state.app.sidebar,
      device: state => state.app.device,
      needTagsView: state => state.settings.tagsView,
      fixedHeader: state => state.settings.fixedHeader
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },
    variables() {
      return variables;
    }
  },
  data() {
    return {
      backupInterval: '1_days',
      isNeedBackup: false,
      backupTables: []
    }
  },
  created() {

    setInterval(() => {
      let requests = []
      requests.push(new Promise(resolve => {
        // 查询备份频率
        getDicts('backup_interval').then(res => {
          if (res.data && res.data.length > 0) {
            this.backupInterval = res.data[0].dictValue;
          }
          resolve()
        })
      }))

      requests.push(new Promise(resolve => {
        // 查询需要备份的表
        getDicts('backup_tables').then(res => {
          if (!res.data) {
            this.backupTables = [];
          } else {
            this.backupTables = res.data.map(o => o.dictValue);
          }
          resolve()
        })
      }))
      Promise.allSettled(requests).then(res => {
        this.handleCheckBackup();
      })
    }, 5*60*1000);
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    },
    handleCheckBackup() {
      if (this.backupTables.length === 0) {
        return;
      }
      if (this.isNeedBackup) {
        return;
      }
      listBackupLog({pageSize: 1, pageNum: 1, orderByColumn: 'backup_time', isAsc: 'desc'}).then(res => {
        let data = res.rows;
        if (!data || data.length === 0 || !this.backupInterval || this.backupInterval.indexOf('_') < 0) {
          // 提醒备份
          this.isNeedBackup = true;
        } else {
          // 检查是否需要备份
          let amount = this.backupInterval.split('_')[0];
          let unit = this.backupInterval.split('_')[1];
          let lastBackupTime = moment(data[0].backupTime)
          let nextBackupTime = moment(lastBackupTime).add(amount, unit);
          if (moment().isAfter(nextBackupTime)) {
            this.isNeedBackup = true;
          }
        }
        if (this.isNeedBackup) {
          // 提醒备份
          const h = this.$createElement;
          let notify = this.$notify({
            title: '备份数据提醒',
            dangerouslyUseHTMLString: true,
            message: h('div', null, [
              h('p', {style: 'color: red;'}, '请及时备份数据。'),
              h('button', {
                'class': 'backup-btn', on: {
                  click: () => {
                    this.handleBackup();
                    notify.close()
                  }
                }
              }, '立即备份')
            ]),
            duration: 0,
            onClose: () => {this.isNeedBackup = false}
          });

        }
      })
    },
    handleBackup() {
      let requests = []
      this.backupTables.forEach(table => {
        requests.push(new Promise((resolve, reject) => {
          getTableData({table: table}).then(res => {
            resolve({table: table, data: res.data})
          }).catch(() => {
            reject(table);
          })
        }))
      })
      Promise.allSettled(requests).then(res => {
        let succeeded = res.filter(o => o.status === "fulfilled");
        let failed = res.filter(o => o.status !== "fulfilled");
        let workBook = XLSX.utils.book_new();
        succeeded.map(o => o.value).forEach(value => {
          let workSheet = XLSX.utils.json_to_sheet(value.data);
          XLSX.utils.book_append_sheet(workBook, workSheet, value.table);
        })
        XLSX.writeFile(workBook, moment().format(process.env.VUE_APP_TITLE + '_数据备份_YYYY-MM-DD_HH-mm-ss') + '.xlsx', {
          bookType: 'xlsx', // 输出的文件类型
          type: 'buffer', // 输出的数据类型
          compression: true // 开启zip压缩
        })

        if (succeeded.length === this.backupTables.length) {
          // 全部成功
          this.$notify({
            title: '成功',
            message: '备份文件已下载，请妥善保管。',
            type: 'success'
          });
          // 记录备份日志
          addBackupLog({backupTime: moment().format('YYYY-MM-DD HH:mm:ss')})
          this.isNeedBackup = false;
        } else {
          // 部分失败
          let failedTables = failed.map(o => o.reason).join(',');
          this.$notify.error({
            title: '错误',
            message: '这些表备份失败，请稍后再试。' + failedTables
          });
          this.isNeedBackup = false;
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/assets/styles/mixin.scss";
  @import "~@/assets/styles/variables.scss";

  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
      position: fixed;
      top: 0;
    }
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  .fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 9;
    width: calc(100% - #{$base-sidebar-width});
    transition: width 0.28s;
  }

  .hideSidebar .fixed-header {
    width: calc(100% - 54px);
  }

  .sidebarHide .fixed-header {
    width: 100%;
  }

  .mobile .fixed-header {
    width: 100%;
  }

  .backup-btn {
    border: solid 1px #4d9efc;
    border-radius: 3px;
    background-color: #4d9efc;
    color: white;
    cursor: pointer;
    padding: 2px 5px;
  }

  .backup-btn:hover {
    background-color: #6eb1fd;
    border: solid 1px #6eb1fd;
  }

</style>
