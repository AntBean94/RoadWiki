<template>
  <div>
    <div
      class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center profile-header"
      style="min-height: 600px; background-image: url(img/theme/profile-cover.jpg); background-size: cover; background-position: center top;"
    >
      <b-container fluid>
        <!-- Mask -->
        <span class="mask bg-gradient-default opacity-5"></span>
        <!-- Header container -->
        <b-container fluid class="d-flex align-items-center">
          <b-row>
            <b-col lg="7" md="10">
              <!-- 닉네임 들어갈 부분 -->
              <h1 class="display-2 text-white opacity-8">{{ nickname }}</h1>
              <!-- 한줄 소개 들어갈 부분 -->
              <p class="text-white mt-0 mb-3">
                {{ introduction }}
                <!--이거 나중에 글자 깨지는거 고쳐주세요 명 상 명희가 명상을하면 명명상 -->
              </p>
              <div>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M19 0h-14c-2.761 0-5 2.239-5 5v14c0 2.761 2.239 5 5 5h14c2.762 0 5-2.239 5-5v-14c0-2.761-2.238-5-5-5zm-4.466 19.59c-.405.078-.534-.171-.534-.384v-2.195c0-.747-.262-1.233-.55-1.481 1.782-.198 3.654-.875 3.654-3.947 0-.874-.312-1.588-.823-2.147.082-.202.356-1.016-.079-2.117 0 0-.671-.215-2.198.82-.64-.18-1.324-.267-2.004-.271-.68.003-1.364.091-2.003.269-1.528-1.035-2.2-.82-2.2-.82-.434 1.102-.16 1.915-.077 2.118-.512.56-.824 1.273-.824 2.147 0 3.064 1.867 3.751 3.645 3.954-.229.2-.436.552-.508 1.07-.457.204-1.614.557-2.328-.666 0 0-.423-.768-1.227-.825 0 0-.78-.01-.055.487 0 0 .525.246.889 1.17 0 0 .463 1.428 2.688.944v1.489c0 .211-.129.459-.528.385-3.18-1.057-5.472-4.056-5.472-7.59 0-4.419 3.582-8 8-8s8 3.581 8 8c0 3.533-2.289 6.531-5.466 7.59z"
                  />
                </svg>
                <b-link
                  href="https://github.com/Jo-Myounghee"
                  class="text-white opacity-8"
                >
                  {{ address }}
                </b-link>
              </div>
              <br />
              <!-- v-if 해서 uid랑 해당 계정의 id가 같을 때만 수정하기 버튼 활성화 -->
              <router-link
                :to="{ name: 'profile-update' }"
                class="btn btn-primary"
                v-if="!isSearch"
                >수정하기</router-link
              >
              <b-button
                variant="primary"
                v-show="isSearch && !isFollow"
                @click="sendFollowing"
                >팔로우 하기</b-button
              >
              <b-button
                variant="danger"
                v-show="isSearch && isFollow"
                @click="sendUnfollowing"
                >팔로우 취소</b-button
              >
            </b-col>
          </b-row>
        </b-container>
      </b-container>
    </div>

    <b-container fluid class="mt--6">
      <b-card no-body class="card-profile" alt="Image placeholder" img-top>
        <b-row class="justify-content-center">
          <b-col lg="3" class="order-lg-2">
            <div class="card-profile-image">
              <a href="#">
                <!-- <b-img src="img/theme/team-4.jpg" rounded="circle" /> -->
                <b-avatar :src="profileUrl"></b-avatar>
              </a>
            </div>
          </b-col>
        </b-row>

        <b-card-header
          class="text-center border-0 pt-8 pt-md-4 pb-0 pb-md-4 mb-4"
        >
        </b-card-header>
        <b-card-body class="pt-0">
          <b-row>
            <b-col>
              <div
                class="card-profile-stats d-flex justify-content-center mt-md-5"
              >
                <div>
                  <b-button v-b-modal.modal-follower class="heading">{{
                    followerlist.length
                  }}</b-button>
                  <span class="description">follower</span>
                  <b-modal id="modal-follower">
                    {{ followerlist }}
                    {{ followinglist }}
                    <div>follow1</div>
                    <div>follow2</div>
                    여기다가 follower 리스트를 보여주는 걸로 하자!
                  </b-modal>
                </div>
                <div>
                  <span class="heading">{{ followinglists.length }}</span>
                  <span class="description">following</span>
                </div>
                <div>
                  <span class="heading">{{ postings.length }}</span>
                  <span class="description">게시글</span>
                </div>
                <div>
                  <!-- 현재는 로드맵 갯수와 연결 -->
                  <span class="heading">{{ commentCnt }}</span>
                  <span class="description">댓글</span>

                </div>
              </div>
            </b-col>
          </b-row>
          <div class="text-center">
            <h1 class="display-2">
              {{ nickname }}
            </h1>
            <div class="mb-2 h5">
              <i class="ni ni-hat-3 mr-2"></i> {{ major }}
            </div>
            <div class="h3 font-weight-300">
              <b-badge
                variant="warning"
                class="mx-1"
                v-for="(keywordtext, idx) in keywordtexts"
                :key="idx"
              >
                {{ keywordtext }}
                <!-- 네 너가 다시 수정하세요 -->
              </b-badge>
            </div>
            <hr class="my-4" />
            <b-container>
              <b-row class="h3 justify-content-center">
                <!-- <i class="ni ni-palette mr-2"></i> -->
                {{ nickname }}님의 로드맵 ({{ roadmaps.length }})
              </b-row>
              <b-row
                v-for="(roadmap, idx) in roadmaps"
                :key="idx"
                class="justify-content-center"
              >
                <b-link href="" class="opacity-8">
                  {{ roadmap.name }}
                </b-link>
              </b-row>
            </b-container>
            <hr class="my-4" />
            <b-container>
              <b-row class="h3 justify-content-center">
                <!-- <i class="ni ni-palette mr-2"></i> -->
                {{ nickname }}님의 게시글 ({{ postings.length }})
              </b-row>
              <b-row
                v-for="(posting, idx) in postings"
                :key="idx"
                class="justify-content-center"
              >
                <b-link href="" class="opacity-8">
                  {{ posting.title }}
                </b-link>
              </b-row>
            </b-container>
            <hr class="my-4" />
            <b-container>
              <b-row class="h3 justify-content-center">
                <!-- <i class="ni ni-palette mr-2"></i> -->
                {{ nickname }}님이 좋아요한 게시글 ({{ likepostings.length }})
              </b-row>
              <b-row
                v-for="(likeposting, idx) in likepostings"
                :key="idx"
                class="justify-content-center"
              >
                <b-link href="" class="opacity-8">
                  {{ likeposting.title }}
                </b-link>
              </b-row>
            </b-container>
            <hr class="my-4" />
            <b-container>
              <b-row class="h3 justify-content-center">
                <!-- <i class="ni ni-palette mr-2"></i> -->
                {{ nickname }}님이 댓글단 게시글 ({{ commentpostings.length }})
              </b-row>
              <b-row
                v-for="(commentposting, idx) in commentpostings"
                :key="idx"
                class="justify-content-center"
              >
                <b-link href="" class="opacity-8">
                  {{ commentposting.title }}
                </b-link>
              </b-row>
            </b-container>
            <hr class="my-4" />
          </div>
        </b-card-body>
      </b-card>
    </b-container>
  </div>
</template>
<script>
import UserCard from "./UserProfile/UserCard.vue";

export default {
  components: {
    UserCard
  },
  props: ["youruid"],
  data() {
    return {
      nickname: "",
      introduction:
        "술잔을 들자하니 천하가 내발아래 있고 6팀 친구들 또한 옆에 있으니 염라대왕 두렵지 않구나",
      address: "https://github.com/",
      profileImg: "",
      backImg: "",
      keywordtexts: [],
      followerlist: [],
      followinglist: [],
      boards: "",
      comments: "",
      major: "기계공학",
      email: "",
      oneline: "",
      uid: "",
      isSearch: true,
      profileUrl: "",
      profileUid: 0,
      isFollow: false,
      roadmaps: [],
      postings: [],
      likepostings: [],
      commentpostings: [],
      commentCnt: 0,
      followerlists: [],
      followinglists: [],
    };
  },
  created() {
    // 현재 로그인 된 계정
    this.uid = this.$store.getters.getUid;

    // params로 uid를 받아오지 못했다면 (자기 프로필이니까)
    if (this.$route.query.profileId === undefined) {
      this.profileuid = this.uid;
    } else {
      this.profileuid = this.$route.query.profileId;
    }

    // 해당 계정의 프로필 사진 가져오는 걸로 해야함
    axios.get(`${this.$store.getters.getUserServer}/user/image/${this.profileuid}`).then(res => {
      this.profileUrl = res.data.path;
    });

    axios
      .get(`${this.$store.getters.getUserServer}/user/info/${this.profileuid}`)
      .then(res => {
        if (res.data.isEqual) {
          this.isSearch = false;
        } else {
          this.isSearch = true;
        }
        this.nickname = res.data.name;
        this.email = res.data.email;
        this.keywords = res.data.keywords;
        this.keywordtexts = res.data.keywordtexts;
        this.address = res.data.address;
        this.major = res.data.major;
        this.oneline = res.data.oneline;
      })
      .catch(err => {
        alert("로그인이 필요한 서비스입니다.");
        this.$store.dispatch("LOGOUT").then(() => {
          this.$router.replace("/");
        });
      });

    this.getFollowList();

    // 본인 로드맵 가져오기
    axios
      .get(
        `${this.$store.getters.getRoadmapServer}/roadmap/list/${this.profileuid}`
      )
      .then(res => {
        this.roadmaps = res.data.roadmaps;
      })
      .catch(err => {});

    // 본인 게시글 가져오기
    axios
      .get(
        `${this.$store.getters.getBoardServer}/freeboard/list/${this.profileuid}`
      )
      .then(res => {
        this.postings = res.data.postings;
        this.commentCnt = res.data.commentCnt;
      })
      .catch(err => {});

    // 본인 좋아요 게시글 가져오기
    axios
      .get(
        `${this.$store.getters.getBoardServer}/freeboard/likelist/${this.profileuid}`
      )
      .then(res => {
        this.likepostings = res.data.likepostings;
      })
      .catch(err => {});

    // 본인 댓글 게시글 가져오기
    axios
      .get(
        `${this.$store.getters.getBoardServer}/freeboard/commentlist/${this.profileuid}`
      )
      .then(res => {
        this.commentpostings = res.data.commentpostings;
      })
      .catch(err => {});

    // 본인 팔로워 리스트 가져오기
    axios
      .get(
        `${this.$store.getters.getUserServer}/follow/followerlist/${this.profileuid}`
      )
      .then(res => {
        this.followerlists = res.data.followerlists;
      })
      .catch(err => {});

    // 본인 팔로우 리스트 가져오기
    axios
      .get(
        `${this.$store.getters.getUserServer}/follow/followinglist/${this.profileuid}`
      )
      .then(res => {
        this.followinglists = res.data.followinglists;
      })
      .catch(err => {});
  },
  methods: {
    getFollowList() {
      axios
        .get(
          `${this.$store.getters.getUserServer}/follow/list/${this.profileuid}`
        )
        .then(res => {
          this.followerlist = res.data.followers;
          this.followinglist = res.data.followings;
          if (res.data.isFollow) {
            this.isFollow = true;
          } else {
            this.isFollow = false;
          }
        });
    },
    sendFollowing() {
      let follow = {
        touid: `${this.profileuid}`
      };
      axios
        .post(`${this.$store.getters.getUserServer}/follow/userfollow`, follow)
        .then(res => {
          if (res.data.msg === "success") {
            this.isFollow = true;
          }
          this.getFollowList();
        });
    },
    sendUnfollowing() {
      axios
        .delete(
          `${this.$store.getters.getUserServer}/follow/userunfollow/${this.profileuid}`
        )
        .then(res => {
          if (res.data.msg === "success") {
            this.isFollow = false;
          }
          this.getFollowList();
        });
    }
  }
};
</script>
<style></style>
