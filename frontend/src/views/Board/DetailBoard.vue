<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>


    <b-container style="background: white; border-radius: 1rem;" class="py-4 mt-4">
      <b-row>
        <h1 class="ml-3 mb-0">{{ title }}</h1>
      </b-row>
      <hr class="my-2">
      <b-row>
        <b-col cols="1" class="pr-0 mb-2">? 무엇 img 들어갈 곳</b-col>
        <b-col>
          <h3>
            작성자 : {{ name }}
          </h3>
          <h5>
            {{ createDate }} / 0000년 00월 00일 00:00 수정됨
          </h5>
        </b-col>
      </b-row>
      <hr class="my-2">
      <b-row>
        <b-container>
          <Viewer v-if="content != null" :initialValue="content" />
        </b-container>
        <!-- <p class="px-3">
          {{ content }}
        </p> -->
      </b-row>
      <b-row align-h="end" class="my-2" v-if="isWriter">
        <!-- 게시물의 uid와 현재 uid가 동일 할 때 보여줄 내용 -->
        <i class="far fa-trash-alt fa-lg mr-3" style="color: tomato" @click="deleteBoard"></i>
        <i class="far fa-edit fa-lg mr-3" style="color: Dodgerblue" @click="updateBoard"></i>
      </b-row>
      <b-row>
        <i class="far fa-thumbs-up fa-2x ml-3" v-if="!like" @click="clickLike"><span class="h3 ml-1">좋아요{{likeCnt}}</span></i>
        <i class="fas fa-thumbs-up fa-2x ml-3" v-if="like" @click="cancelLike"><span class="h3 ml-1">좋아요{{likeCnt}}</span></i>
        <i class="far fa-thumbs-down fa-2x ml-3" v-if="!dislike" @click="clickDislike"><span class="h3 ml-1">싫어요{{dislikeCnt}}</span></i>
        <i class="fas fa-thumbs-down fa-2x ml-3" v-if="dislike" @click="cancelDislike"><span class="h3 ml-1">싫어요{{dislikeCnt}}</span></i>
      </b-row>
    </b-container>

    <b-container style="background: white; border-radius: 1rem;" class="py-4 mt-4">
      <b-row>
        <h2 class="ml-4 mb-0">댓글({{ comments.length }})</h2>
      </b-row>
      <hr class="my-2">
      <!-- 댓글 폼 필요 -->
      
      <CommentForm :pid="pid" @sendComment="getPostingInfo"/>      
      <!-- <CommentList :comments="comments" :recomments="recomments"/>       -->
      <b-container v-for="(comment, idx) in comments" :key="idx">
        <CommentList :comment="comment" :recomments="recomments[idx]" @sendRecomment="getPostingInfo"/>      
      </b-container>
    </b-container>
  </div>
</template>

<script>
import CommentForm from '@/components/Board/CommentForm.vue';
import CommentList from '@/components/Board/CommentList.vue';
import "codemirror/lib/codemirror.css"; 
import "@toast-ui/editor/dist/toastui-editor.css"; 
import { Viewer } from "@toast-ui/vue-editor";

export default {
  name: '',
  components: {
    CommentForm,
    CommentList,
    Viewer
  },
  data() {
    return {
      name: '',
      content: null,
      title: '',
      createDate: '',
      modifyDate: '',
      uid: '',
      classifier: '',
      like: false,
      dislike: false,
      likeCnt: '',
      dislikeCnt: '',
      pid: '',
      isWriter: false,
      comments: [],
      recomments: [],
    }
  },
  created() {
    axios.get(`${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`)
    .then((res) => {
      this.uid =  res.data.posting.uid
      if (this.uid === this.$store.getters.getUid) {
        this.isWriter = true
      } else {
        this.isWriter = false
      }
    })

    axios.get(`${this.$store.getters.getServer}/freeboard/postinglike/${this.$route.query.pid}`)
    .then((res) => {
      console.log('여기는 라이크 정보')
      console.log(res.data)
      if (res.data.like !== null) {
        this.like = true
      }
    })
  },
  mounted() {
    this.getPostingInfo()
  },
  methods: {
    getPostingInfo() {
      axios.get(`${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`)
      .then((res) => {
        console.log(res.data)
        this.name = res.data.name
        this.classifier = res.data.posting.classifier
        this.title = res.data.posting.title
        this.content = res.data.posting.content
        this.createDate = res.data.posting.createDate
        this.modifyDate = res.data.posting.modifyDate
        this.uid = res.data.posting.uid
        this.likeCnt = res.data.posting.likeCnt
        this.pid = res.data.posting.pid
        this.comments = res.data.comments
        this.recomments = res.data.recomments
      })

      .catch((err) => {
        console.log(err)
      })
    },
    updateBoard() {
      const pid = this.$route.query.pid
      this.$router.push({name: 'update_board', query: { pid }})
    },
    deleteBoard() {
      axios.delete(`${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`)
      .then(() => {
        alert('삭제가 정상적으로 완료되었습니다.')
        this.$router.push('/tmp_board')
      })
    },
    clickLike() {
      const postingLikeUser = {
        'pid': this.$route.query.pid,
      }

      axios.post(`${this.$store.getters.getServer}/freeboard/postinglike`, postingLikeUser)
      .then((res) => {
        console.log(res)
      })

      if (this.dislike) {
        this.cancelDislike()
      }
      this.like = true
      this.likeCnt ++
    },
    cancelLike() {
      axios.delete(`${this.$store.getters.getServer}/freeboard/postinglikecancel/${this.$route.query.pid}`)
      .then(() => {
      })

      this.like = false
      this.likeCnt -- 
    },
    clickDislike() {
      if (this.like) {
        this.cancelLike()
      }
      this.dislike = true
      this.dislikeCnt ++
    },
    cancelDislike() {
      this.dislike = false
      this.dislikeCnt --
    },
  },
}
</script>

<style>

</style>