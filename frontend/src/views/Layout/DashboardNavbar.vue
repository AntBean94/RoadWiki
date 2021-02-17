<template>
  <div>
    <base-nav
      id="mainNav"
      container-classes="container-fluid"
      class="navbar-top navbar-expand"
      :class="{ 'navbar-dark': type === 'default' }"
      v-if="isHeader"
    >
      <a
        @click="goToMain"
        class="h1 mb-0 mr-2 text-black text-uppercase d-none d-lg-inline-block active router-link-active"
      >
        <img src="/img/brand/logo_word.png" alt="roadwiki" width="200rem;" />
      </a>

      <a
        href="#"
        aria-current="page"
        class="h4 mb-0 mt-2 d-none d-lg-inline-block active router-link-active"
        id="routeName"
      >
        {{ $route.name }}
      </a>

      <b-navbar-nav class="align-items-center ml-md-auto">
        <!-- This item dont have <b-nav-item> because item have data-action/data-target on tag <a>, wich we cant add -->
        <li class="nav-item d-sm-none">
          <a
            class="nav-link"
            href="#"
            data-action="search-show"
            data-target="#navbar-search-main"
          >
            <i class="ni ni-zoom-split-in"></i>
          </a>
        </li>
      </b-navbar-nav>

      <!-- 나누기 -->
      <b-navbar-nav class="align-items-center ml-auto ml-md-0">
        <div v-if="isSearch" class="row" id="menu">
          <div v-if="isHoverO">
            <i @mouseover="checkHoverO" class="btn ni ni-tv-2 text-black"></i>
          </div>
          <div v-else>
            <h1
              @mouseleave="checkHoverO"
              @click="goToOfficial"
              class="btn m-0 p-1"
            >
              Official
            </h1>
          </div>

          <div v-if="isHoverB">
            <i
              @mouseover="checkHoverB"
              class="btn ni ni-bullet-list-67 text-black"
            ></i>
          </div>
          <div v-else>
            <h1
              @mouseleave="checkHoverB"
              @click="goToBoard"
              class="btn m-0 p-1"
            >
              Board
            </h1>
          </div>

          <div v-if="isHoverS">
            <i 
              @mouseover="checkHoverS"
              class="fas fa-share-alt
            "></i>
          </div>
          <div v-else>
            <h1
              @mouseleave="checkHoverS"
              @click="goToShare"
              class="btn m-0 p-1"
            >
              Share
            </h1>
          </div>

          <div v-if="isHoverC">
            <i
              @mouseover="checkHoverC"
              class="btn ni ni-bullet-list-67 text-black"
            ></i>
          </div>
          <div v-else>
            <h1 @mouseleave="checkHoverC" @click="goToChat" class="btn m-0 p-1">
              Chat
            </h1>
          </div>
        </div>

        <div v-else>
          <b-form
            class="navbar-search form-inline mr-sm-3"
            :class="{
              'navbar-search-dark': type === 'default',
              'navbar-search-light': type === 'light'
            }"
            id="navbar-search-main"
          >
            <b-form-group class="mb-0">
              <b-input-group
                class="input-group-alternative input-group-merge"
                id="searchBlock"
              >
                <b-form-input
                  placeholder="Search"
                  type="text"
                  v-model="searchQuery"
                  @keydown.enter="clickSearch"
                >
                </b-form-input>

                <div class="input-group-append">
                  <span class="input-group-text" @click="clickSearch">
                    <i class="fas fa-search"></i>
                  </span>
                </div>
              </b-input-group>
            </b-form-group>
          </b-form>
        </div>

        <div>
          <i @click="activeSearch" class="btn fas fa-search text-black"></i>
        </div>
        <!-- 여기부터 이미지, 드롭다운 리스트 -->

        <base-dropdown
          menu-on-right
          class="nav-item"
          tag="li"
          title-tag="a"
          title-classes="nav-link pr-0"
        >
          <a
            href="#"
            class="nav-link pr-0"
            @click.prevent
            slot="title-container"
          >
            <b-media no-body class="align-items-center">
              <span class="avatar avatar-sm rounded-circle">
                <b-img
                  alt="Image placeholder"
                  :src="profileUrl"
                  v-model="profileUrl"
                />
              </span>
              <b-media-body class="ml-2 d-none d-lg-block">
                <!-- <span class="mb-0 text-sm  font-weight-bold">John Snow</span> -->
                <span class="mb-0 text-sm  font-weight-bold">{{
                  this.$store.getters.getName
                }}</span>
              </b-media-body>
            </b-media>
          </a>

          <template>
            <b-dropdown-header class="noti-title">
              <h6 class="text-overflow m-0">Welcome!</h6>
            </b-dropdown-header>
            <b-dropdown-item @click="myProfile">
              <i class="ni ni-single-02"></i>
              <span>나의 프로필</span>
            </b-dropdown-item>
            <!-- <b-dropdown-item href="#!">
              <i class="ni ni-settings-gear-65"></i>
              <span>Settings</span>
            </b-dropdown-item> -->
            <b-dropdown-item @click="goToCalendar">
              <i class="ni ni-calendar-grid-58"></i>
              <span>캘린더</span>
            </b-dropdown-item>
            <!-- <b-dropdown-item href="#!">
              <i class="ni ni-support-16"></i>
              <span>Support</span>
            </b-dropdown-item> -->
            <div class="dropdown-divider"></div>
            <b-dropdown-item @click="logOut">
              <i class="ni ni-user-run"></i>
              <span>Logout</span>
            </b-dropdown-item>
          </template>
        </base-dropdown>
      </b-navbar-nav>
    </base-nav>

    <!-- 여기까지가 nav 입니다. -->

    <div class="main-content">
      <!-- <dashboard-navbar :type="$route.meta.navbarType"></dashboard-navbar> -->

      <div @click="$sidebar.displaySidebar(false)">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
      </div>

      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
    <div>
      <Chatting v-on:remove="removeChatting" v-if="chattingOn" />
      <button id="chat" class="btn" @click="createChatting" v-else>
        chatting
      </button>
    </div>
  </div>
</template>
<script>
import PerfectScrollbar from "perfect-scrollbar";
import "perfect-scrollbar/css/perfect-scrollbar.css";
import LoginContent from "@/components/Login/LoginContent.vue";
import LogoutContent from "@/components/Logout/LogoutContent.vue";

function hasElement(className) {
  return document.getElementsByClassName(className).length > 0;
}

function initScrollbar(className) {
  if (hasElement(className)) {
    new PerfectScrollbar(`.${className}`);
  } else {
    // try to init it later in case this component is loaded async
    setTimeout(() => {
      initScrollbar(className);
    }, 100);
  }
}

import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import { FadeTransition } from "vue2-transitions";
import { mapGetters } from "vuex";
import Chatting from "@/components/Chatting/Chatting";

import { CollapseTransition } from "vue2-transitions";
import { BaseNav, Modal } from "@/components";

export default {
  components: {
    CollapseTransition,
    BaseNav,
    Modal,
    ContentFooter,
    DashboardContent,
    FadeTransition,
    LoginContent,
    LogoutContent,
    Chatting
  },
  props: {
    type: {
      type: String,
      default: "default", // default|light
      description:
        "Look of the dashboard navbar. Default (Green) or light (gray)"
    }
  },
  data() {
    return {
      activeNotifications: false,
      showMenu: false,
      searchModalVisible: false,
      searchQuery: "",
      isHeader: true,
      uid: "",
      profileUrl: "",
      chattingOn: false,
      urlNow: "",
      isHoverO: true,
      isHoverB: true,
      isHoverS: true,
      isHoverC: true,
      isSearch: true
    };
  },
  created() {
    this.uid = this.$store.getters.getUid;
    let url = this.$route.name;
    this.checkUrl(url);
    this.url = url;

    axios.get(`${this.$store.getters.getServer}/user/image`).then(res => {
      this.profileUrl = res.data.path;
      console.log(this.profileUrl);
    });
  },
  computed: {
    routeName() {
      const { name } = this.$route;
      return this.capitalizeFirstLetter(name);
    }
  },
  watch: {
    $route(to) {
      this.checkUrl(to.name);
      this.url = to.name;
    }
  },
  mounted() {
    this.initScrollbar();
  },
  methods: {
    initScrollbar() {
      let isWindows = navigator.platform.startsWith("Win");
      if (isWindows) {
        initScrollbar("sidenav");
      }
    },
    removeChatting() {
      this.chattingOn = false;
    },
    createChatting() {
      this.chattingOn = true;
    },
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    toggleNotificationDropDown() {
      this.activeNotifications = !this.activeNotifications;
    },
    closeDropDown() {
      this.activeNotifications = false;
    },
    checkUrl(url) {
      let array = ["roadmap", "update_user_roamdap", "roadback"];
      let isHeader = true;
      array.map(path => {
        if (url === path) isHeader = false;
      });
      this.isHeader = isHeader;
    },
    myProfile() {
      this.$router.push({
        name: "프로필",
        query: { profileId: this.$store.getters.getUid }
      });
    },
    logOut() {
      this.$store
        .dispatch("LOGOUT")
        .then(() => {
          this.$router.push("/");
        })
        .catch(() => {
          alert("로그아웃에 실패했습니다.");
        });
    },
    clickSearch() {
      console.log(this.searchQuery);
      this.$router.push({
        name: "검색결과",
        query: { searchKeyword: `${this.searchQuery}` }
      });
    },
    goToMain() {
      if (this.url != "read_user_roadmap") {
        this.$router.push({ name: "dashboard" });
      }
    },
    checkHoverO() {
      if (this.isHoverO) {
        this.isHoverO = false;
      } else {
        this.isHoverO = true;
      }
    },
    checkHoverB() {
      if (this.isHoverB) {
        this.isHoverB = false;
      } else {
        this.isHoverB = true;
      }
    },
    checkHoverS() {
      if (this.isHoverS) {
        this.isHoverS = false;
      } else {
        this.isHoverS = true;
      }
    },
    checkHoverC() {
      if (this.isHoverC) {
        this.isHoverC = false;
      } else {
        this.isHoverC = true;
      }
    },
    goToOfficial() {
      this.$router.push({ name: "공식 로드맵" });
    },
    goToBoard() {
      this.$router.push({ name: "게시판" });
    },
    goToShare() {
      this.$router.push({ name: "공유로드맵's" });
    },
    goToCalendar() {
      this.$router.push({ name: "캘린더" });
    },
    goToChat() {
      this.$router.push({ name: "채팅카테고리" });
    },
    activeSearch() {
      if (this.isSearch) {
        this.isSearch = false;
      } else {
        this.isSearch = true;
      }
    }
  },
  watch: {
    $route(to) {
      this.checkUrl(to.name);
    }
  },
  computed: {
    ...mapGetters(["getAccessToken"])
  }
};
</script>

<style>
#chat {
  position: fixed;
  right: 5px;
  bottom: 5px;
  background-color: skyblue;
}
#mainNav {
  background-color: white;
  padding: 10px;
  box-shadow: 2px 2px blur;
}
#routeName {
  color: #84898c;
}
#btnNav {
  height: 20px;
  width: 55px;
}
#searchBlock {
  color: black;
  text-decoration-color: black;
}
#menu {
  margin: auto;
}
/* #testdiv {
  position: fixed;
  right: 0;
  top: 0;
  width: 30%;
  height: 100%;
  overflow: hidden;
  background-color: darkred;
} */
@font-face {
  font-family: "San Francisco";
  font-weight: 400;
  src: url("https://applesocial.s3.amazonaws.com/assets/styles/fonts/sanfrancisco/sanfranciscodisplay-regular-webfont.woff");
}
</style>
