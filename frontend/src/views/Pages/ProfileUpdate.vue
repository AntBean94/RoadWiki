<template>
  <div>
    <div
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center profile-header"
      style="
        min-height: 600px;
        background-image: url(img/theme/profile-cover.jpg);
        background-size: cover;
        background-position: center top;
      "
    >
      <b-container class="align-items-center">
        <span class="mask bg-gradient-default opacity-5"></span>
        <!-- <b-row class="justify-content-end"><BackgroundImg /></b-row> -->
      </b-container>
    </div>

    <b-container fluid class="mt--6">
      <b-card no-body class="card-profile" alt="Image placeholder" img-top>
        <b-row class="justify-content-start">
          <b-col lg="3" class="order-lg-2">
            <b-container class="card-profile-image">
              <b-row>
                <b-img :src="rqprofileURL" rounded="circle" />
              </b-row>
              <b-row class="justify-content-end"> </b-row>
            </b-container>
          </b-col>
        </b-row>

        <b-card-header
          class="text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4 mb-4"
        >
          <h1 class="display-1">
            <!-- email.com -->
          </h1>
        </b-card-header>
        <b-card-body class="pt-0">
          <b-row>
            <b-col>
              <div
                class="card-profile-stats d-flex justify-content-center mt-md-5"
              ></div>
            </b-col>
          </b-row>
          <b-container>
            <!-- <hr class="my-4"> -->
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  닉네임
                </h2>
              </b-col>
              <b-col>
                <b-form-input
                  :value="nickname"
                  v-model="nickname"
                ></b-form-input>
              </b-col>
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  전공
                </h2>
              </b-col>
              <b-col>
                <b-form-input value="기계공학"></b-form-input>
              </b-col>
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  블로그
                </h2>
              </b-col>
              <b-col>
                <!-- address 수정 -->
                <b-form-input :value="address"></b-form-input>
              </b-col>
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  한 줄 소개
                </h2>
              </b-col>
              <b-col>
                <b-form-textarea
                  :value="introduction"
                  v-model="introduction"
                  rows="5"
                >
                </b-form-textarea>
              </b-col>
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  관심 개발 분야
                </h2>
              </b-col>
              <b-col cols="7" align-self="center">
                <b-badge
                  variant="warning"
                  class="mx-1"
                  v-for="(keyword, idx) in keywordtexts"
                  :key="idx"
                >
                  {{ keyword }}
                </b-badge>
              </b-col>
              <FlavourContent 
                class="col align-self-center pl-5 ml-5" 
                @changeFlavour="changeFlavour"
                :keywords="keywords"
              />
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  프로필 사진
                </h2>
              </b-col>
              <b-col>
                <h3>여기에 프로필 사진 파일 명</h3>
                <!-- <ProfileImg/> -->
              </b-col>
              <b-col>
                <div>
                  <b-button size="sm" @click="modalShow = !modalShow"
                    >사진📷</b-button
                  >

                  <b-modal v-model="modalShow" hide-footer>
                    <template #modal-title>
                      <h1>프로필 업로드</h1>
                    </template>
                    <div>
                      <b-form-file
                        v-model="file1"
                        placeholder="Choose a file or drop it here..."
                        drop-placeholder="Drop file here..."
                      ></b-form-file>
                      <div class="mt-3">
                        Selected file: {{ file1 ? file1.name : "" }}
                      </div>
                    </div>
                    <div class="text-center">
                      <base-button
                        type="primary"
                        native-type="submit"
                        class="my-4"
                        @click="uploadHandler"
                        >확인</base-button
                      >
                    </div>
                  </b-modal>
                </div>
              </b-col>
            </b-row>
            <b-row class="mb-3">
              <b-col cols="3" class="text-center" align-self="center">
                <h2>
                  <!-- <i class="ni ni-hat-3 mr-2"></i> -->
                  배경 사진
                </h2>
              </b-col>
              <b-col>
                <h3>여기에 배경 사진 파일 명</h3>
                <!-- <ProfileImg/> -->
              </b-col>
              <b-col>
                <div>
                  <b-button size="sm" @click="modalShow = !modalShow"
                    >사진📷</b-button
                  >

                  <b-modal v-model="modalShow" hide-footer>
                    <template #modal-title>
                      <h1>프로필 업로드</h1>
                    </template>
                    <div>
                      <b-form-file
                        v-model="file1"
                        placeholder="Choose a file or drop it here..."
                        drop-placeholder="Drop file here..."
                      ></b-form-file>
                      <div class="mt-3">
                        Selected file: {{ file1 ? file1.name : "" }}
                      </div>
                    </div>
                    <div class="text-center">
                      <base-button
                        type="primary"
                        native-type="submit"
                        class="my-4"
                        @click="uploadHandler"
                        >확인</base-button
                      >
                    </div>
                  </b-modal>
                </div>
              </b-col>
            </b-row>

            <hr class="my-4" />
            <b-row class="justify-content-end">
              <b-button
                variant="danger"
                class="mt-4 mr-4"
                @click="withDrawal"
                size="sm"
                >회원탈퇴</b-button
              >
            </b-row>
            <b-row class="justify-content-center">
              <b-button
                variant="primary"
                class="mt-4"
                size="lg"
                @click="updateHandler"
                >정보수정</b-button
              >
            </b-row>
          </b-container>
        </b-card-body>
      </b-card>
    </b-container>
  </div>
</template>
<script>
// import EditProfileForm from './UserProfile/EditProfileForm.vue';
import UserCard from "./UserProfile/UserCard.vue";
import LoginContent from "@/components/Login/LoginContent.vue";
import FlavourContent from "@/components/Profileupdate/FlavourContent.vue";
// import ProfileImg from '@/components/Profileupdate/ProfileImg.vue';
// import BackgroundImg from "@/components/Profileupdate/BackgroundImg.vue";

export default {
  components: {
    // EditProfileForm,
    UserCard,
    LoginContent,
    FlavourContent
    // ProfileImg,
    // BackgroundImg
  },
  data() {
    return {
      nickname: "",
      introduction:
        "술잔을 들자하니 천하가 내발아래 있고 6팀 친구들 또한 옆에 있으니 염라대왕 두렵지 않구나",
      address: "https://github.com",
      profileImg: "",
      backImg: "",
      keywords: [],
      keywordtexts: [],
      options: [],
      follower: "",
      following: "",
      boards: "",
      comments: "",
      major: "",
      email: "",
      modalShow: false,
      file1: null,
      files: [],
      uid: "",
      profileURL: ""
    };
  },
  computed: {
    rqprofileURL: function() {
      return require(this.profileURL);
    }
  },
  created() {
    this.uid = this.$store.getters.getUid;
    this.profileURL = `(${this.$store.getters.getServer}/user/image/${this.uid})`;
    axios
      .get(`${this.$store.getters.getServer}/user/info`)
      .then(res => {
        console.log(res.data);
        this.nickname = res.data.name;
        this.email = res.data.email;
        this.keywords = res.data.keywords;
        this.keywordtexts = res.data.keywordtexts;
      })
      .catch(() => {
        alert("로그인이 필요한 서비스입니다.");
        this.$store.dispatch("LOGOUT").then(() => {
          this.$router.replace("/");
        });
      });
    axios.get(`${this.$store.getters.getServer}/keyword/list`).then(res => {
      this.options = res.data.keywords;
    });
  },
  methods: {
    withDrawal() {
      axios
        .delete(`${this.$store.getters.getServer}/user/withdraw`)
        .then(() => {
          alert("회원 탈퇴가 완료되었습니다.");
          this.$store.dispatch("LOGOUT").then(() => {
            this.$router.replace("/main");
          })
        })
        .catch(() => {
          alert("오류가 발생했습니다. 다시 시도해주세요.");
        });
    },
    changeFlavour(keywords) {
      // 다이렉트로 넣기
      console.log(keywords)
      let tempkeywordtexts = new Array(keywords.length)
      for (let i=0; i < keywords.length; i++) {
        tempkeywordtexts[i] = this.options[keywords[i]-1].word
      }
      this.keywordtexts = tempkeywordtexts
      this.keywords = keywords
    },
    updateHandler() {
      // 보낼때 명명이 중요함
      let user = {
        name: this.nickname,
        address: this.address,
        major: this.major,
        keyword: this.keywords,
        introduction: this.introduction,
        file: this.file1
      };
      //console.log("updateHandler : " + user);
      axios
        .put(`${this.$store.getters.getServer}/user/modify`, user)
        .then(res => {
          console.log(res.data);
          if (res.data.msg == "success") {
            alert("회원 수정이 완료되었습니다.");
            this.$router.push("/profile");
          } else alert("회원 수정 시 문제가 발생했슴다");
        });
    },
    async uploadHandler() {
      this.modalShow = false;
      var formData = new FormData();
      formData.append("file", this.file1);
      // var photoFile = document.getElementById("photo");
      // frm.append("photo", photoFile.files[0]);

      // this.modalShow = false;
      //console.log("upload 시작 ");
      //console.log(this.files);

      await axios
        .post(`${this.$store.getters.getServer}/user/pic`, formData, {
          headers: { "content-type": "multipart/form-data" }
        })
        .then(res => {
          //console.log("upload 성공 ");
          //console.log(res.data.path);
          this.profileURL = `${this.$store.getters.getServer}/user/image/${this.uid}`;
        });
    }
  }
};
</script>
<style></style>
