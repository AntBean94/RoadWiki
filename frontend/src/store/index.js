import axios from "axios";
import Vue from "vue";
import Vuex from "vuex";
// import axios from "axios";

Vue.use(Vuex);

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
// console.log(SERVER_URL)

export default new Vuex.Store({
  state: {
    server: SERVER_URL,
    accessToken: null,
    user: {
      uid: "",
      uid: "",
      email: "",
      name: "",
      createDate: ""
    },
    code: "",
    pid: ""
  },
  getters: {
    getServer(state) {
      return state.server;
    },
    getAccessToken(state) {
      return state.accessToken;
    },
    getUid(state) {
      return state.user.uid;
    },
    getEmail(state) {
      return state.user.email;
    },
    getName(state) {
      return state.user.name;
    },
    getCreateDate(state) {
      return state.user.createDate;
    },
    getCode(state) {
      return state.code;
    },
    getUser(state) {
      return state.user;
    },
    getPid(state) {
      return state.pid;
    }
  },
  mutations: {
    LOADUSERTOKEN(state) {
      console.log("set token");
      state.accessToken = sessionStorage.getItem("auth-token");
      state.user = JSON.parse(sessionStorage.getItem("user"));
      console.log("load : " + state.user);
      axios.defaults.headers.common["auth-token"] = state.accessToken;
    },
    LOGIN(state, payload) {
      state.accessToken = payload["authorizationToken"];
      state.user.uid = payload["uid"];
      state.user.email = payload["email"];
      state.user.name = payload["name"];
      state.user.createDate = payload["createDate"];
    },
    LOGOUT(state) {
      state.accessToken = null;
      state.user.uid = "";
      state.user.email = "";
      state.user.name = "";
      state.user.createDate = "";
    },
    SETINFO(state, payload) {
      state.user.name = payload["name"];
      state.user.email = payload["email"];
    },
    SETEMAIL(state, payload) {
      state.user.email = payload;
    },
    SETCODE(state, payload) {
      state.code = payload;
    },
    SETPID(state, payload) {
      state.pid = payload;
    }
  },
  actions: {
    LOGIN(context, user) {
      return axios
        .post(`${SERVER_URL}/user/login`, user)
        .then(response => {
          console.log(response.data);
          context.commit("LOGIN", response.data);
          if (`${response.data["authorizationToken"]}` == "undefined") reject();
          axios.defaults.headers.common[
            "auth-token"
          ] = `${response.data["authorizationToken"]}`;
          sessionStorage.setItem(
            "auth-token",
            `${response.data["authorizationToken"]}`
          );
          sessionStorage.setItem("user", JSON.stringify(this.getters.getUser));
        })
        .catch(() => {
          reject();
        });
    },
    LOGOUT(context) {
      context.commit("LOGOUT");
      axios.defaults.headers.common["auth-token"] = null;
      sessionStorage.removeItem("auth-token");
    },
    SETINFO(context, user) {
      context.commit("SETINFO", user);
    },
    SETCODE(context, code) {
      context.commit("SETCODE", code);
    },
    SETEMAIL(context, email) {
      context.commit("SETEMAIL", email);
    },
    SETPID(context, pid) {
      context.commit("SETPID", pid);
    }
  },
  modules: {}
});