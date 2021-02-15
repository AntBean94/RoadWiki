<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>

    <b-container class="bg-baby-blue">
      <b-container class="px-9 py-3">
        <b-row class="py-5">
          <h1 class="text-white">{{ title }}</h1>
        </b-row>
        <b-row>
          <p class="text-white">{{ createDate }}</p>
        </b-row>
        <b-row v-if="modifyDate">
          <p class="text-white">{{ modifyDate }}</p>
        </b-row>
      </b-container>
    </b-container>
    <b-container
      class="py-4 mt-4"
    >
      <b-row>
        <b-container class="mx-9">
          <b-row align-v="center">
            <b-col cols="1" class="pr-0 mb-2 mr-3">img</b-col>
            <b-col>
              <h3>{{ name }}</h3>
              <h5>한줄 내용 넣고싶음</h5>
            </b-col>
          </b-row>
          <hr class="my-2" />
          <Viewer v-if="content != null" :initialValue="content" />
          <b-row class="mr-1 my-3">
            <div
              class="ml-1 mt-2 mr-1 bg-cornhusk rounded-pill py-1 px-2"
              v-for="(tag, idx) in tags"
              :key="idx"
            >
              <span class="h5"># {{ tag }}</span>
            </div>
          </b-row>
          <b-row align-h="end" class="my-2" v-if="isWritter">
            <!-- 게시물의 uid와 현재 uid가 동일 할 때 보여줄 내용 -->
            <i
              class="far fa-trash-alt fa-lg mr-3"
              style="color: tomato"
              @click="deleteBoard"
            ></i>
            <i
              class="far fa-edit fa-lg mr-3"
              style="color: Dodgerblue"
              @click="updateBoard"
            ></i>
          </b-row>
          <b-row>
            <i class="far fa-thumbs-up fa-2x ml-3 text-peach-quartz" v-if="!like" @click="clickLike"
              ><span class="h3 ml-1">좋아요{{ likeCnt }}</span></i
            >
            <i class="fas fa-thumbs-up fa-2x ml-3 text-peach-quartz" v-if="like" @click="cancelLike"
              ><span class="h3 ml-1">좋아요{{ likeCnt }}</span></i
            >
            <i
              class="far fa-thumbs-down fa-2x ml-3 text-provence"
              v-if="!dislike"
              @click="clickDislike"
              ><span class="h3 ml-1">싫어요{{ dislikeCnt }}</span></i
            >
            <i
              class="fas fa-thumbs-down fa-2x ml-3 text-provence"
              v-if="dislike"
              @click="cancelDislike"
              ><span class="h3 ml-1">싫어요{{ dislikeCnt }}</span></i
            >
          </b-row>
          <hr class="my-2" />
        </b-container>
        <!-- <p class="px-3">
          {{ content }}
        </p> -->
      </b-row>
    </b-container>

    <b-container
      class="py-4 mt-4"
    >
      <b-container class="px-9 py-3">
        <b-row>
          <h2 class="ml-3 mb-2">{{ comments.length }}개의 댓글</h2>
        </b-row>
        <!-- 댓글 폼 필요 -->
        <CommentForm :pid="pid" @sendComment="getPostingInfo" />
        <!-- <CommentList :comments="comments" :recomments="recomments"/>       -->
        <b-container v-for="(comment, idx) in comments" :key="idx">
          <CommentList
            :comment="comment"
            :recomments="recomments[idx]"
            :idx="idx"
            @sendRecomment="getPostingInfo"
          />
        </b-container>
      </b-container>
    </b-container>
  </div>
</template>

<script>
import CommentForm from "@/components/Board/CommentForm.vue";
import CommentList from "@/components/Board/CommentList.vue";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Viewer } from "@toast-ui/vue-editor";

export default {
  name: "",
  components: {
    CommentForm,
    CommentList,
    Viewer
  },
  data() {
    return {
      name: "",
      content: null,
      title: "",
      tags: [],
      createDate: "",
      modifyDate: "",
      uid: "",
      classifier: "",
      like: false,
      dislike: false,
      likeCnt: 0,
      dislikeCnt: 0,
      pid: "",
      isWritter: false,
      comments: [],
      recomments: []
    };
  },
  created() {
    axios
      .get(
        `${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`
      )
      .then(res => {
        this.uid = res.data.posting.uid;
        this.tags = res.data.posting.tags;
        if (this.uid === this.$store.getters.getUid) {
          this.isWritter = true;
        } else {
          this.isWritter = false;
        }
      });

    axios
      .get(
        `${this.$store.getters.getServer}/freeboard/postinglike/${this.$route.query.pid}`
      )
      .then(res => {
        if (res.data.like !== null) {
          this.like = true;
        }
        this.likeCnt = res.data.likeCnt;
      });

    axios
      .get(
        `${this.$store.getters.getServer}/freeboard/postinghate/${this.$route.query.pid}`
      )
      .then(res => {
        if (res.data.like !== null) {
          this.dislike = true;
        }
        this.dislikeCnt = res.data.dislikeCnt;
      });
  },
  mounted() {
    this.getPostingInfo();
  },
  methods: {
    getPostingInfo() {
      axios
        .get(
          `${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(res => {
          this.name = res.data.name;
          this.classifier = res.data.posting.classifier;
          this.title = res.data.posting.title;
          this.content = res.data.posting.content;
          this.createDate = res.data.posting.createDate;
          this.modifyDate = res.data.posting.modifyDate;
          this.uid = res.data.posting.uid;
          this.pid = res.data.posting.pid;
          this.comments = res.data.comments;
          this.recomments = res.data.recomments;
          this.tags = res.data.posting.tags;
        })
        .catch(err => {});
    },
    updateBoard() {
      const pid = this.$route.query.pid;
      this.$router.push({ name: "글 수정하기", query: { pid } });
    },
    deleteBoard() {
      axios
        .delete(
          `${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`
        )
        .then(() => {
          alert("삭제가 정상적으로 완료되었습니다.");
          this.$router.push("/tmp_board");
        });
    },
    clickLike() {
      const postingLikeUser = {
        pid: this.$route.query.pid
      };
      axios
        .post(
          `${this.$store.getters.getServer}/freeboard/postinglike`,
          postingLikeUser
        )
        .then(res => {
          this.likeCnt++;
          axios
            .get(
              `${this.$store.getters.getServer}/freeboard/postinghate/${this.$route.query.pid}`
            )
            .then(res => {
              if (res.data.like !== null) {
                axios.delete(
                  `${this.$store.getters.getServer}/freeboard/postinghatecancel/${this.$route.query.pid}`
                );
                this.dislikeCnt--;
              }
            });
        });

      if (this.dislike) {
        this.cancelDislike();
      }
      this.like = true;
    },
    cancelLike() {
      axios
        .delete(
          `${this.$store.getters.getServer}/freeboard/postinglikecancel/${this.$route.query.pid}`
        )
        .then(() => {});

      this.like = false;
      this.likeCnt--;
    },
    clickDislike() {
      const postingLikeUser = {
        pid: this.$route.query.pid
      };
      axios
        .post(
          `${this.$store.getters.getServer}/freeboard/postinghate`,
          postingLikeUser
        )
        .then(res => {
          this.dislikeCnt++;
          axios
            .get(
              `${this.$store.getters.getServer}/freeboard/postinglike/${this.$route.query.pid}`
            )
            .then(res => {
              if (res.data.like !== null) {
                axios.delete(
                  `${this.$store.getters.getServer}/freeboard/postinglikecancel/${this.$route.query.pid}`
                );
                this.likeCnt--;
              }
            });
        });

      if (this.like) {
        this.cancelLike();
      }
      this.dislike = true;
    },
    cancelDislike() {
      axios
        .delete(
          `${this.$store.getters.getServer}/freeboard/postinghatecancel/${this.$route.query.pid}`
        )
        .then(() => {});
      this.dislike = false;
      this.dislikeCnt--;
    }
  }
};
</script>

<style></style>
