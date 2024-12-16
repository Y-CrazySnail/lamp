<template>
  <div class="login-container">
    <div class="login-background-container">
      <img src="../../assets/login_bg.png" class="login-background-image" />
    </div>
    <div class="login-form-container">
      <div class="login-form">
        <img src="../../assets/logo.svg" style="width: 200px; height: 50px" />
        <div style="color: #566a7f; font-size: 22px; margin-top: 30px">
          Welcome to LampCloud! 👋
        </div>
        <div style="color: #566a7f; font-size: 14px; margin: 7px 0 15px 0">
          请登录您的账号开始体验！
        </div>
        <el-form
          ref="loginForm"
          :model="loginForm"
          :rules="loginRules"
          style="width: 80%"
        >
          <el-form-item label="账号" prop="pass">
            <el-input type="text" v-model="loginForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="checkPass">
            <el-input type="password" v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              :loading="loading"
              type="primary"
              style="width: 100%; margin-top: 30px"
              @click.native.prevent="handleLogin"
            >
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-container {
  display: flex;
  background-color: #fff;
  width: 100vw;
  height: 100vh;
}

.login-form {
  width: 100%;
  height: 50%;
  padding-left: 15%;
}

@media (max-width: 991px) {
  .login-background-container {
    display: block;
  }

  .login-background-image {
    width: 0;
  }

  .login-form-container {
    display: flex;
    align-items: center;
    width: 100%;
    height: 100%;
  }
}

@media (min-width: 991px) {
  .login-background-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 62%;
    background: #f3f9ff;
  }

  .login-background-image {
    max-width: 100%;
    height: auto;
    vertical-align: middle;
  }

  .login-form-container {
    display: flex;
    align-items: center;
    width: 38%;
    height: 100%;
  }
}

.el-form-item {
  margin-bottom: 5px;
}
</style>


<script>
import { validUsername } from "@/utils/validate";

export default {
  name: "Login",
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error("Please enter the correct user name"));
      } else {
        callback();
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error("The password can not be less than 6 digits"));
      } else {
        callback();
      }
    };
    return {
      functionType: "login",
      loginForm: {
        username: "",
        password: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", validator: validateUsername },
        ],
        password: [
          { required: true, trigger: "blur", validator: validatePassword },
        ],
      },
      loading: false,
      passwordType: "password",
      redirect: undefined,
      // 重置用户名
      resetUsername: "",
      // 验证码
      validateCode: "",
      // 重置密码
      resetPassword: "",
      // 确认重置密码
      resetConfirmPassword: "",
      // 验证码冷却
      coolDownFlag: false,
      // 验证码时间
      coolDownTime: 0,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  methods: {
    switchFunctionType(type) {
      this.functionType = type;
    },
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    handleLogin() {
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.loginForm);
          await this.$store
            .dispatch("auth/login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" });
              this.loading = false;
            })
            .catch((err) => {
              this.$message({
                message: "账号或密码错误",
                type: "error",
              });
              this.loading = false;
            });
          await this.$store.dispatch("auth/getInfo");
          location.reload();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    /**
     * 获取验证码
     */
    async getValidateCode() {
      this.coolDownFlag = true;
      let form = {};
      form.username = this.resetUsername;
      this.$store
        .dispatch("user/getValidateCode", form)
        .then((response) => {
          this.$message.success(response);
        })
        .catch(() => {});
      this.coolDownTime = 60;
      while (this.coolDownTime > 0) {
        await this.sleep(1000);
        this.coolDownTime = this.coolDownTime - 1;
      }
      this.coolDownFlag = false;
    },
    /**
     * 睡眠
     * @param {} time
     */
    sleep(time) {
      return new Promise((resolve) => {
        setTimeout(() => {
          resolve();
        }, time);
      });
    },
    updateByValidateCode() {
      if (this.resetPassword !== this.resetConfirmPassword) {
        this.$message.error("密码输入不一致，请重新输入");
        return;
      }
      let form = {};
      form.username = this.resetUsername;
      form.password = this.resetPassword;
      form.validateCode = this.validateCode;
      this.$store
        .dispatch("user/updateByValidateCode", form)
        .then((response) => {
          this.$message.success(response);
          this.resetUsername = "";
          this.resetPassword = "";
          this.resetConfirmPassword = "";
          this.validateCode = "";
          this.functionType = "login";
        })
        .catch((e) => {
          this.$message.error(e.response.data);
        });
    },
  },
};
</script>

