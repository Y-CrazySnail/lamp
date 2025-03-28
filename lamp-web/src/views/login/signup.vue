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
          <el-form-item label="邮箱" prop="pass">
            <el-input type="text" v-model="loginForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="checkPass">
            <el-input type="password" v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input
              type="password"
              v-model="loginForm.checkPassword"
            ></el-input>
          </el-form-item>
          <el-form-item label="推荐码">
            <el-input type="text" v-model="loginForm.referrerCode"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              :loading="loading"
              type="primary"
              style="width: 100%; margin-top: 30px"
              @click.native.prevent="handleSignup"
            >
              注册
            </el-button>
          </el-form-item>
          <div
            style="
              display: flex;
              justify-content: space-between;
              margin: 20px 0 10px 0;
            "
          >
            <el-link :underline="false" type="primary" @click="login">
              登录
            </el-link>
            <el-link :underline="false" type="info">
              出现问题联系右下角客服
            </el-link>
          </div>
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
  mounted() {
    let referrerCode = this.$route.query.referrerCode;
    this.loginForm.referrerCode = referrerCode;
  },
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
        checkPassword: "",
        referrerCode: "",
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
    login() {
      this.$router.push({ path: "/login" });
    },
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
    handleSignup() {
      if (this.loginForm.password !== this.loginForm.checkPassword) {
        this.$message.error("密码输入不一致，请重新输入");
        return;
      }
      if (!this.loginForm.referrerCode) {
        this.$message.error("请输入推荐码");
        return;
      }
      this.$store
        .dispatch("auth/signup", this.loginForm)
        .then(() => {
          this.$message.success("注册成功");
          this.$router.push({ path: "/login" });
        })
        .catch((e) => {
          this.$message.error(e.response.data);
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

