<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="日期范围" prop="daterange">
        <el-date-picker clearable
          v-model="queryParams.daterange"
          type="daterange"
          value-format="yyyy-MM-dd"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
                        @change="handleSelectDateRange"
        >
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="月份" prop="orderMonth">-->
<!--        <el-date-picker-->
<!--          v-model="queryParams.orderMonth"-->
<!--          type="month"-->
<!--          value-format="yyyyMM"-->
<!--          placeholder="选择月">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item label="车号" prop="carNumber">
        <el-input
          v-model="queryParams.carNumber"
          placeholder="请输入车号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名" prop="clientName">
        <el-input
          v-model="queryParams.clientName"
          placeholder="请输入客户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="装货地" prop="loadingPlace">
        <el-input
          v-model="queryParams.loadingPlace"
          placeholder="请输入装货地"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卸货地" prop="unloadingPlace">
        <el-input
          v-model="queryParams.unloadingPlace"
          placeholder="请输入卸货地"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货物名称" prop="goodsName">
        <el-input
          v-model="queryParams.goodsName"
          placeholder="请输入货物名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卸货时间" prop="unloadingTime">
        <el-date-picker clearable
          v-model="queryParams.unloadingTime"
          type="datetime"
          format="yyyy-MM-dd HH:mm"
          value-format="yyyy-MM-dd HH:mm"
          placeholder="请选择卸货时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="付款时间" prop="paymentTime">
        <el-date-picker clearable
          v-model="queryParams.paymentTime"
          type="datetime"
          format="yyyy-MM-dd HH:mm"
          value-format="yyyy-MM-dd HH:mm"
          placeholder="请选择付款时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="司机" prop="driverName">
        <el-input
          v-model="queryParams.driverName"
          placeholder="请输入司机"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="趟次" prop="trips">
        <el-input
          v-model="queryParams.trips"
          placeholder="请输入趟次"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['orderInfo:baseinfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['orderInfo:baseinfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['orderInfo:baseinfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['orderInfo:baseinfo:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExportAll"
          v-hasPermi="['orderInfo:baseinfo:export']"
        >导出全部</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-upload
          class="import-button"
          ref="import-button"
          :show-file-list="false"
          :on-change="handleImportStockList"
          action=""
          :auto-upload="false"
          style="display: inline; margin: 0 15px"
        >
          <el-button slot="trigger" size="mini" type="primary" plain>从文件导入</el-button>
        </el-upload>
        <el-link @click="downloadTemplate" style="padding-bottom: 3px;margin-right: 15px"><i class="el-icon-download"></i>下载模板</el-link>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="baseinfoList" @selection-change="handleSelectionChange" :cell-style="cellStyle" @sort-change="sortTable">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日期" align="center" prop="orderDate" width="180" sortable="custom">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车号" align="center" prop="carNumber" />
      <el-table-column label="客户名" align="center" prop="clientName" />
      <el-table-column label="装货地" align="center" prop="loadingPlace" />
      <el-table-column label="卸货地" align="center" prop="unloadingPlace" />
      <el-table-column label="货物名称" align="center" prop="goodsName" />
      <el-table-column label="运费" align="center" prop="freight" sortable="custom"/>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="卸货时间" align="center" prop="unloadingTime" width="180" sortable="custom">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.unloadingTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="付款时间" align="center" prop="paymentTime" width="180" sortable="custom">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="司机" align="center" prop="driverName" />
      <el-table-column label="趟次" align="center" prop="trips" sortable="custom"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['orderInfo:baseinfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['orderInfo:baseinfo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改报表信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日期" prop="orderDate">
          <el-date-picker clearable
            v-model="form.orderDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车号" prop="carNumber">
          <el-select
            v-model="form.carNumber"
            filterable
            allow-create
            placeholder="请选择或创建">
            <el-option
              v-for="item in carList"
              :key="item.carNumber"
              :label="item.carNumber"
              :value="item.carNumber">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户名" prop="clientName">
          <el-input v-model="form.clientName" placeholder="请输入客户名" />
        </el-form-item>
        <el-form-item label="装货地" prop="loadingPlace">
          <el-input v-model="form.loadingPlace" placeholder="请输入装货地" />
        </el-form-item>
        <el-form-item label="卸货地" prop="unloadingPlace">
          <el-input v-model="form.unloadingPlace" placeholder="请输入卸货地" />
        </el-form-item>
        <el-form-item label="货物名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入货物名称" />
        </el-form-item>
        <el-form-item label="运费" prop="freight">
          <el-input-number v-model="form.freight" :min="0" :precision="2" ></el-input-number>
        </el-form-item>
        <el-form-item label="卸货时间" prop="unloadingTime">
          <el-date-picker clearable
            v-model="form.unloadingTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm"
            placeholder="请选择卸货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="付款时间" prop="paymentTime">
          <el-date-picker clearable
            v-model="form.paymentTime"
            type="datetime"
            format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm"
            placeholder="请选择付款时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="司机" prop="driverName">
          <el-input v-model="form.driverName" placeholder="请输入司机" />
        </el-form-item>
        <el-form-item label="趟次" prop="trips">
          <el-input-number v-model="form.freight" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="标红" prop="isMarked" placeholder="请选择">
          <el-select v-model="form.isMarked" placeholder="请选择">
            <el-option
              v-for="item in [{label: '否', value: '0'}, {label: '是', value: '1'}]"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
<!--    导入文件对话框-->
    <el-dialog
      title="导入"
      :visible.sync="openImportDialog"
      width="30%"
      :close-on-click-modal="false"
    >
      <el-card shadow="never">
        <p>文件：<b>{{importDataInfo.fileName}}</b></p>
        <p>包含 <b>{{importDataInfo.total}} </b>条记录</p>
      </el-card>
      <p v-if="importingStatus.isSaving"><i class="el-icon-loading"></i> 正在导入</p>
      <p v-if="importDataInfo.total > 0 && !importingStatus.isSaving && importingStatus.success" style="color: #67c23a"><i class="el-icon-success"></i> 导入完成</p>
      <p v-if="importDataInfo.total > 0 && !importingStatus.isSaving && importingStatus.failed" style="color: #f56c6c"><i class="el-icon-error"></i> 导入失败，已完成前 {{importingStatus.finishCount}} 条，请检查文件内容格式是否正确</p>
      <p v-if="importingStatus.isSaving || importingStatus.success">已完成 {{importingStatus.finishCount}} / {{importDataInfo.total}}</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="openImportDialog = false" :disabled="importingStatus.isSaving">关 闭</el-button>
        <el-button v-if="!importingStatus.isSaving && !importingStatus.success" type="primary" @click="handleSaveImportData" :disabled="importDataInfo.total === 0">开始导入</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {addBaseinfo, delBaseinfo, getBaseinfo, listBaseinfo, updateBaseinfo} from "@/api/orderInfo/baseinfo";
import XLSX from 'xlsx';
import {listCarinfo} from "../../../api/carInfo/carinfo";
import moment from "moment";

export default {
  name: "Baseinfo",
  data() {
    return {
      openImportDialog: false, // 导入弹窗
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报表信息表格数据
      baseinfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderDate: null,
        carNumber: null,
        clientName: null,
        loadingPlace: null,
        unloadingPlace: null,
        goodsName: null,
        freight: null,
        unloadingTime: null,
        paymentTime: null,
        driverName: null,
        trips: null,
        isMarked: null,
        daterange: null
      },
      // 表单参数
      form: {
        isMarked: '0'
      },
      // 表单校验
      rules: {
      },
      // 等待导入的数据
      importDataInfo: {
        fileName: null,
        data: [],
        total: 0
      },
      importingStatus: {
        isSaving: false,
        finishCount: 0,
        success: false,
        failed: false
      },
      // 车辆列表
      carList : []
    };
  },
  created() {
    this.getList();
    this.getCarList()
  },
  methods: {
    cellStyle({row, column, rowIndex, columnIndex}){
      if (row.isMarked === '1') {
        return 'color: #f56c6c;'
      }
    },
    /** 查询报表信息列表 */
    getList() {
      this.loading = true;
      listBaseinfo(this.queryParams).then(response => {
        this.baseinfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createTime: null,
        updateTime: null,
        delFlag: null,
        orderDate: null,
        carNumber: null,
        clientName: null,
        loadingPlace: null,
        unloadingPlace: null,
        goodsName: null,
        freight: null,
        remark: null,
        unloadingTime: null,
        paymentTime: null,
        driverName: null,
        trips: null,
        isMarked: '0'
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.startDate = null;
      this.queryParams.endDate = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getCarList();
      this.open = true;
      this.title = "添加报表信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getCarList();
      const id = row.id || this.ids
      getBaseinfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报表信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 校验
          // 运费
          // if (!new RegExp('^(\\-|\\+)?\\d+(\\.\\d+)?$', 'g').test(this.form.freight)) {
          //   this.$alert('运费须为数字')
          //   return
          // }
          if (this.form.id != null) {
            updateBaseinfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBaseinfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delBaseinfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('orderInfo/baseinfo/export', {
        ...this.queryParams
      }, `${moment().format('YYYY-MM-DD_HH_mm')}.xlsx`)
    },
    /** 导出全部按钮操作 */
    handleExportAll() {
      this.download('orderInfo/baseinfo/export', {
      }, `全部数据_${moment().format('YYYY-MM-DD_HH_mm')}.xlsx`)
    },
    sortTable(param) {
      Object.assign(this.queryParams,this.generateTableSortParam(param));
      this.getList()
    },
    // 驼峰转换下划线
    camelToLine(str) {
      return str.replace(/([A-Z])/g, '_$1').toLowerCase()
    },
// 生成排序参数
    generateTableSortParam(param, sortWithoutNull) {
      let column = this.camelToLine(param.prop)
      if (sortWithoutNull) {
        return {
          orderByColumn: column + ' is null ,' + column,
          isAsc: param.order.split("ending")[0]
        }
      } else {
        return {
          orderByColumn: column,
          isAsc: param.order.split("ending")[0]
        }
      }
    },
    handleImportStockList(file) {
      if (file.raw.type !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
        this.$message.error('请选择 Excel 文件');
        return;
      }
      // reset importDataInfo
      this.importDataInfo = {
        fileName: null,
        data: [],
        total: 0
      }
      console.log(file);
      this.importDataInfo.fileName = file.name;
      let f = file.raw;
      let reader = new FileReader();
      let binary = "";
      let wb;
      let data;
      reader.onload = () => {
        let bytes = new Uint8Array(reader.result);
        let length = bytes.byteLength;
        for (let i = 0; i < length; i++) {
          binary += String.fromCharCode(bytes[i]);
        }
        wb = XLSX.read(binary, {
          type: 'binary'
        });
        data = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
        this.importDataInfo.data = data;
        this.importDataInfo.total = data.length;
        this.openImportDialog = true;
        // reset importingStatus
        this.importingStatus = {
          isSaving: false,
          finishCount: 0,
          success: false
        }
      }
      reader.readAsArrayBuffer(f);
    },
    async handleSaveImportData() {
      let data = this.importDataInfo.data;
      let total = this.importDataInfo.total;
      if (total === 0) {
        return;
      }
      this.importingStatus.isSaving = true;
      let keyMap = {
        "日期": 'orderDate',
        "车号": 'carNumber',
        "客户名": 'clientName',
        "装货地": 'loadingPlace',
        "卸货地": 'unloadingPlace',
        "货物名称": 'goodsName',
        "运费": 'freight',
        "卸货时间": 'unloadingTime',
        "付款时间": 'paymentTime',
        "司机": 'driverName',
        "趟次": 'trips',
        "是否标红": 'isMarked',
      }
      for (const item of data) {
        let tempFormData = {}
        Object.keys(item).forEach(key => {
          tempFormData[keyMap[key]] = item[key];
        })
        try {
          await addBaseinfo(tempFormData).then(res => {
            if (res.code === 200) {
              this.importingStatus.finishCount++;
            }
            if (this.importingStatus.finishCount === total) {
              // 导入完成
              console.log('导入完成');
              this.importingStatus.success = true;
              this.importingStatus.isSaving = false;
            }
          })
        } catch (e) {
          this.importingStatus.failed = true;
        }
      }
    },
    downloadTemplate() {
      let workBook = XLSX.utils.book_new();
      let demoData = [{
        "日期": '',
        "车号": '',
        "客户名": '',
        "装货地": '',
        "卸货地": '',
        "货物名称": '',
        "运费": '',
        "卸货时间": '',
        "付款时间": '',
        "司机": '',
        "趟次": '',
        "是否标红": '',
      }]

      XLSX.utils.book_append_sheet(workBook, XLSX.utils.json_to_sheet(demoData), '模板');
      XLSX.writeFile(workBook, '导入模板.xlsx', {
        bookType: 'xlsx', // 输出的文件类型
        type: 'buffer', // 输出的数据类型
        compression: true // 开启zip压缩
      })

    },
    handleSelectDateRange(value) {
      this.queryParams.startDate = value[0]
      this.queryParams.endDate = value[1]
    },
    getCarList() {
        listCarinfo().then(res => {
          this.carList = res.rows
      })
    }
  }
};
</script>
