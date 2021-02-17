<template>
  <div>
    <!-- <base-header class="pb-2 pt-2 pt-md-2 bg-baby-blue"> -->
    <base-header class="pb-9 pt-9 bg-baby-blue">
      <b-container fluid>
        <!-- Header container -->
        <b-container>
          <b-row>
            <!-- 닉네임 들어갈 부분 -->
            <h1 class="display-2 text-white opacity-8">
              {{ nickname }}
            </h1>
          </b-row>
          <br>
          <b-row>
            <!-- 한줄 소개 들어갈 부분 -->
            <h4 class="text-monument mt-0 mb-3">
              {{ oneline }}
              <!--이거 나중에 글자 깨지는거 고쳐주세요 명 상 명희가 명상을하면 명명상 -->
            </h4>
          </b-row>
          <b-row align-v="center">
            <div>
              <i class="fas fa-home"></i>
              <b-link
                :href="address"
                class="text-classic-blue opacity-8"
              >
                {{ address }}
              </b-link>
            </div>
          </b-row>
          <br>
          <b-row>
            <!-- v-if 해서 uid랑 해당 계정의 id가 같을 때만 수정하기 버튼 활성화 -->
            <router-link
              :to="{ name: 'profile-update' }"
              class="btn btn-cornhusk opacity-8"
              v-if="!isSearch"
              >수정하기</router-link
            >
            <b-button
              variant="classic-blue"
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
          </b-row>
        </b-container>
      </b-container>
    <!-- </div> -->
    </base-header>


    <b-container fluid class="mt--6">
      <b-card no-body class="card-profile" alt="Image placeholder" img-top>
        <b-row class="justify-content-center">
          <b-col lg="3" class="order-lg-2">
            <div class="card-profile-image">
              <a href="#">
                <b-img src="img/theme/team-4.jpg" rounded="circle" />
                <!-- <b-img :src="profileUrl" rounded="circle" /> -->
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
                  <b-button variant="link" v-b-modal.modal-follower class="heading">
                    {{ followerlist.length }}
                  </b-button>
                  <span class="description">follower</span>
                  <b-modal id="modal-follower" hide-footer>
                    <ul v-for="(follower, idx) in followerlist" :key="idx">
                      <!-- following 유저 정보 넘겨주기 -->
                      {{follower}}
                    </ul>
                  </b-modal>
                </div>
                <div>
                  <b-button variant="link" v-b-modal.modal-following class="heading">
                    {{ followinglist.length }}
                  </b-button>
                  <span class="description">following</span>
                  <b-modal id="modal-following" hide-footer>
                    <ul v-for="(following, idx) in followinglist" :key="idx">
                      {{ following }}
                    </ul>
                  </b-modal>
                </div>
                <!-- <div>
                  <b-button variant="link" v-b-modal.modal-posting class="heading">
                    {{ postings.length }}
                  </b-button>
                  <span class="description">게시글</span>
                </div> -->
                <!-- <div>
                  <span class="heading">{{ commentCnt }}</span>
                  <span class="description">댓글</span>
                </div> -->
              </div>
            </b-col>
          </b-row>
          <div class="text-center">
            <!-- <h1 class="display-2">
              {{ nickname }}
            </h1> -->

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
            <hr class="my-4 mx-9" />
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
      introduction: "",
      address: "",
      profileImg: "",
      backImg: "",
      keywordtexts: [],
      followerlist: [],
      followinglist: [],
      boards: "",
      comments: "",
      major: "",
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
    };
  },
  created() {
    // 현재 로그인 된 계정
    this.uid = this.$store.getters.getUid;
    console.log(this.$store.getters.getUid)

    // params로 uid를 받아오지 못했다면 (자기 프로필이니까)
    if (this.$route.query.profileId === undefined) {
      this.profileuid = this.uid;
    } else {
      this.profileuid = this.$route.query.profileId;
    }

    // 해당 계정의 프로필 사진 가져오는 걸로 해야함
    axios.get(`${this.$store.getters.getUserServer}/user/image/${this.profileuid}`)
    .then(res => {
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
        console.log('정보들')
        console.log(res.data)
      })
      .catch(err => {
        alert("로그인이 필요한 서비스입니다.");
        this.$store.dispatch("LOGOUT").then(() => {
          this.$router.replace("/");
        });
      });

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

    // 본인 팔로워, 팔로잉 명단 가져오기
    this.getFollowList();
  },
  methods: {
    getFollowList() {
      axios
        .get(
          `${this.$store.getters.getUserServer}/follow/list/${this.profileuid}`
        )
        .then(res => {
          res.data.followers.forEach(follower => {
            if (follower.touid == this.profileuid) {
              this.followerlist.push(follower.fromuid)
            }
          })
          res.data.followings.forEach(following => {
            if (following.fromuid == this.profileuid) {
              this.followinglist.push(following.touid)
            }
          })
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
