<template>
  <div style=" height: 500px; position: relative; display: flex; flex-direction: column; align-items: center;">
    <el-form ref="form" :model="form" label-width="120px" class="dialog-body">
      <div class="dialog-body-item">
        <el-form-item label="名称" class="inner">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="描述" class="inner">
          <el-input v-model="form.description" />
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="价格(元)" class="inner">
          <el-input v-model="form.price" type="number" />
        </el-form-item>
        <el-form-item label="库存(件)" class="inner">
          <el-input v-model="form.stock" type="number" />
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="销量(件)" class="inner">
          <el-input v-model="form.sale" type="number"></el-input>
        </el-form-item>
        <el-form-item label="权重" class="inner">
          <el-input v-model="form.sort" type="number"></el-input>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="分类" class="inner">
          <el-select v-model="form.categoryId" placeholder="请选择">
            <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="推荐标识" class="inner">
          <el-switch v-model="form.recommendFlag" active-color="#13ce66" inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="一级分销商提成" class="inner">
          <el-input v-model="form.directReferrerRate" type="number"></el-input>
        </el-form-item>
        <el-form-item label="二级分销商提成" class="inner">
          <el-input v-model="form.indirectReferrerRate" type="number"></el-input>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="展示图" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="showFile"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessShowFile"
            :on-remove="onRemoveShowFile">
            <el-button size="small" type="primary" v-if="showFile.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="轮播图1" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="swiperFile1"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessSwiperFile1"
            :on-remove="onRemoveSwiperFile1">
            <el-button size="small" type="primary" v-if="swiperFile1.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="轮播图2" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="swiperFile2"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessSwiperFile2"
            :on-remove="onRemoveSwiperFile2">
            <el-button size="small" type="primary" v-if="swiperFile2.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="轮播图3" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="swiperFile3"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessSwiperFile3"
            :on-remove="onRemoveSwiperFile3">
            <el-button size="small" type="primary" v-if="swiperFile3.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="轮播图4" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="swiperFile4"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessSwiperFile4"
            :on-remove="onRemoveSwiperFile4">
            <el-button size="small" type="primary" v-if="swiperFile4.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="轮播图5" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="swiperFile5"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessSwiperFile5"
            :on-remove="onRemoveSwiperFile5">
            <el-button size="small" type="primary" v-if="swiperFile5.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="详情图1" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile1"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile1"
            :on-remove="onRemoveDetailFile1">
            <el-button size="small" type="primary" v-if="detailFile1.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="详情图2" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile2"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile2"
            :on-remove="onRemoveDetailFile2">
            <el-button size="small" type="primary" v-if="detailFile2.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="详情图3" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile3"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile3"
            :on-remove="onRemoveDetailFile3">
            <el-button size="small" type="primary" v-if="detailFile3.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="详情图4" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile4"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile4"
            :on-remove="onRemoveDetailFile4">
            <el-button size="small" type="primary" v-if="detailFile4.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="详情图5" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile5"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile5"
            :on-remove="onRemoveDetailFile5">
            <el-button size="small" type="primary" v-if="detailFile5.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="详情图6" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile6"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile6"
            :on-remove="onRemoveDetailFile6">
            <el-button size="small" type="primary" v-if="detailFile6.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="详情图7" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile7"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile7"
            :on-remove="onRemoveDetailFile7">
            <el-button size="small" type="primary" v-if="detailFile7.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="详情图8" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile8"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile8"
            :on-remove="onRemoveDetailFile8">
            <el-button size="small" type="primary" v-if="detailFile8.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="详情图9" class="inner-small">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile9"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile9"
            :on-remove="onRemoveDetailFile9">
            <el-button size="small" type="primary" v-if="detailFile9.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
      <div class="dialog-body-item">
        <el-form-item label="详情图10" class="inner" style="width: 780px">
          <el-upload action="https://yeemcloud.com/zero-api/manager-zero-tencent-cos/upload" :file-list="detailFile10"
            list-type="picture" :headers="header" :multiple="false" :limit="1" :on-success="onSuccessDetailFile10"
            :on-remove="onRemoveDetailFile10">
            <el-button size="small" type="primary" v-if="detailFile10.length < 1">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
    </el-form>
    <div class="dialog-footer">
      <el-button size="small" @click="onCancle" style="margin-right: 10px">取消</el-button>
      <el-popconfirm confirm-button-text="确认" cancel-button-text="取消" icon="el-icon-info" title="确认更新？"
        @confirm="onSubmit">
        <el-button slot="reference" type="primary" size="small">修改</el-button>
      </el-popconfirm>
    </div>
  </div>
</template>

<script>
  export default {
    name: "ZeroProductEdit",
    props: {
      form: {
        type: Object,
        required: true,
      },
      categoryList: {
        type: Array,
        required: true,
      },
      editDialogVisible: {
        type: Boolean,
        required: true,
      },
    },
    mounted () {
      console.log(this.categoryList);
      this.showFileList = this.form.zeroProductImageShowList.map((e) => {
        let file = {};
        // file.name = "文件";
        file.url = e.path;
        return file;
      });
      console.log(this.showFileList);
      if (this.form.imageShowPath) {
        this.showFile.push({ url: this.form.imageShowPath });
      }
      if (this.form.imageSwiperPath1) {
        this.swiperFile1.push({ url: this.form.imageSwiperPath1 });
      }
      if (this.form.imageSwiperPath2) {
        this.swiperFile2.push({ url: this.form.imageSwiperPath2 });
      }
      if (this.form.imageSwiperPath3) {
        this.swiperFile3.push({ url: this.form.imageSwiperPath3 });
      }
      if (this.form.imageSwiperPath4) {
        this.swiperFile4.push({ url: this.form.imageSwiperPath4 });
      }
      if (this.form.imageSwiperPath5) {
        this.swiperFile5.push({ url: this.form.imageSwiperPath5 });
      }
      if (this.form.imageDetailPath1) {
        this.detailFile1.push({ url: this.form.imageDetailPath1 });
      }
      if (this.form.imageDetailPath2) {
        this.detailFile2.push({ url: this.form.imageDetailPath2 });
      }
      if (this.form.imageDetailPath3) {
        this.detailFile3.push({ url: this.form.imageDetailPath3 });
      }
      if (this.form.imageDetailPath4) {
        this.detailFile4.push({ url: this.form.imageDetailPath4 });
      }
      if (this.form.imageDetailPath5) {
        this.detailFile5.push({ url: this.form.imageDetailPath5 });
      }
      if (this.form.imageDetailPath6) {
        this.detailFile6.push({ url: this.form.imageDetailPath6 });
      }
      if (this.form.imageDetailPath7) {
        this.detailFile7.push({ url: this.form.imageDetailPath7 });
      }
      if (this.form.imageDetailPath8) {
        this.detailFile8.push({ url: this.form.imageDetailPath8 });
      }
      if (this.form.imageDetailPath9) {
        this.detailFile9.push({ url: this.form.imageDetailPath9 });
      }
      if (this.form.imageDetailPath10) {
        this.detailFile10.push({ url: this.form.imageDetailPath10 });
      }
    },
    computed: {
      dictionaryList () {
        return this.$store.state.dictionary.dictionaryList || [];
      },
    },
    data () {
      return {
        showFileList: [],
        showFile: [],
        swiperFile1: [],
        swiperFile2: [],
        swiperFile3: [],
        swiperFile4: [],
        swiperFile5: [],
        detailFile1: [],
        detailFile2: [],
        detailFile3: [],
        detailFile4: [],
        detailFile5: [],
        detailFile6: [],
        detailFile7: [],
        detailFile8: [],
        detailFile9: [],
        detailFile10: [],
        header: {},
      };
    },
    methods: {
      onSubmit () {
        this.form.imageShowPath =
          this.showFile.length > 0 ? this.showFile[0].url : "";
        this.form.imageSwiperPath1 =
          this.swiperFile1.length > 0 ? this.swiperFile1[0].url : "";
        this.form.imageSwiperPath2 =
          this.swiperFile2.length > 0 ? this.swiperFile2[0].url : "";
        this.form.imageSwiperPath3 =
          this.swiperFile3.length > 0 ? this.swiperFile3[0].url : "";
        this.form.imageSwiperPath4 =
          this.swiperFile4.length > 0 ? this.swiperFile4[0].url : "";
        this.form.imageSwiperPath5 =
          this.swiperFile5.length > 0 ? this.swiperFile5[0].url : "";
        this.form.imageDetailPath1 =
          this.detailFile1.length > 0 ? this.detailFile1[0].url : "";
        this.form.imageDetailPath2 =
          this.detailFile2.length > 0 ? this.detailFile2[0].url : "";
        this.form.imageDetailPath3 =
          this.detailFile3.length > 0 ? this.detailFile3[0].url : "";
        this.form.imageDetailPath4 =
          this.detailFile4.length > 0 ? this.detailFile4[0].url : "";
        this.form.imageDetailPath5 =
          this.detailFile5.length > 0 ? this.detailFile5[0].url : "";
        this.form.imageDetailPath6 =
          this.detailFile6.length > 0 ? this.detailFile6[0].url : "";
        this.form.imageDetailPath7 =
          this.detailFile7.length > 0 ? this.detailFile7[0].url : "";
        this.form.imageDetailPath8 =
          this.detailFile8.length > 0 ? this.detailFile8[0].url : "";
        this.form.imageDetailPath9 =
          this.detailFile9.length > 0 ? this.detailFile9[0].url : "";
        this.form.imageDetailPath10 =
          this.detailFile10.length > 0 ? this.detailFile10[0].url : "";
        console.log(this.form);
        this.$store
          .dispatch("zero_product/update", this.form)
          .then((response) => {
            this.$message.success(response);
            this.onCancle();
          })
          .catch(() => { });
      },
      onCancle () {
        this.$emit("update:editDialogVisible", false);
      },
      onSuccessShowFile (response, file, fileList) {
        this.showFile = [];
        this.showFile.push({ url: response });
      },
      onSuccessSwiperFile1 (response, file, fileList) {
        this.swiperFile1 = [];
        this.swiperFile1.push({ url: response });
      },
      onSuccessSwiperFile2 (response, file, fileList) {
        this.swiperFile2 = [];
        this.swiperFile2.push({ url: response });
      },
      onSuccessSwiperFile3 (response, file, fileList) {
        this.swiperFile3 = [];
        this.swiperFile3.push({ url: response });
      },
      onSuccessSwiperFile4 (response, file, fileList) {
        this.swiperFile4 = [];
        this.swiperFile4.push({ url: response });
      },
      onSuccessSwiperFile5 (response, file, fileList) {
        this.swiperFile5 = [];
        this.swiperFile5.push({ url: response });
      },
      onSuccessDetailFile1 (response, file, fileList) {
        this.detailFile1 = [];
        this.detailFile1.push({ url: response });
      },
      onSuccessDetailFile2 (response, file, fileList) {
        this.detailFile2 = [];
        this.detailFile2.push({ url: response });
      },
      onSuccessDetailFile3 (response, file, fileList) {
        this.detailFile3 = [];
        this.detailFile3.push({ url: response });
      },
      onSuccessDetailFile4 (response, file, fileList) {
        this.detailFile4 = [];
        this.detailFile4.push({ url: response });
      },
      onSuccessDetailFile5 (response, file, fileList) {
        this.detailFile5 = [];
        this.detailFile5.push({ url: response });
      },
      onSuccessDetailFile6 (response, file, fileList) {
        this.detailFile6 = [];
        this.detailFile6.push({ url: response });
      },
      onSuccessDetailFile7 (response, file, fileList) {
        this.detailFile7 = [];
        this.detailFile7.push({ url: response });
      },
      onSuccessDetailFile8 (response, file, fileList) {
        this.detailFile8 = [];
        this.detailFile8.push({ url: response });
      },
      onSuccessDetailFile9 (response, file, fileList) {
        this.detailFile9 = [];
        this.detailFile9.push({ url: response });
      },
      onSuccessDetailFile10 (response, file, fileList) {
        this.detailFile10 = [];
        this.detailFile10.push({ url: response });
      },
      onRemoveShowFile (file, fileList) {
        this.showFile = [];
      },
      onRemoveSwiperFile1 (file, fileList) {
        this.swiperFile1 = [];
      },
      onRemoveSwiperFile2 (file, fileList) {
        this.swiperFile2 = [];
      },
      onRemoveSwiperFile3 (file, fileList) {
        this.swiperFile3 = [];
      },
      onRemoveSwiperFile4 (file, fileList) {
        this.swiperFile4 = [];
      },
      onRemoveSwiperFile5 (file, fileList) {
        this.swiperFile5 = [];
      },
      onRemoveDetailFile1 (file, fileList) {
        this.detailFile1 = [];
      },
      onRemoveDetailFile2 (file, fileList) {
        this.detailFile2 = [];
      },
      onRemoveDetailFile3 (file, fileList) {
        this.detailFile3 = [];
      },
      onRemoveDetailFile4 (file, fileList) {
        this.detailFile4 = [];
      },
      onRemoveDetailFile5 (file, fileList) {
        this.detailFile5 = [];
      },
      onRemoveDetailFile6 (file, fileList) {
        this.detailFile6 = [];
      },
      onRemoveDetailFile7 (file, fileList) {
        this.detailFile7 = [];
      },
      onRemoveDetailFile8 (file, fileList) {
        this.detailFile8 = [];
      },
      onRemoveDetailFile9 (file, fileList) {
        this.detailFile9 = [];
      },
      onRemoveDetailFile10 (file, fileList) {
        this.detailFile10 = [];
      },
    },
  };
</script>

<style>
  .dialog-footer {
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    bottom: 0px;
  }

  .dialog-body {
    width: 100%;
    height: 430px;
    position: absolute;
    top: 0;
    overflow-y: scroll;
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  .dialog-body-item {
    width: 100%;
    height: auto;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .dialog-body-item .inner {
    width: 300px;
  }

  .dialog-body-item .inner-small {
    width: 260px;
  }
</style>