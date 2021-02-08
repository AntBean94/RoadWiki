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
      <b-row align-h="end" class="my-2">
        <!-- 게시물의 uid와 현재 uid가 동일 할 때 보여줄 내용 -->
        <i class="far fa-trash-alt fa-lg mr-3" style="color: tomato"></i>
        <i class="far fa-edit fa-lg mr-3" style="color: Dodgerblue" @click="updateBoard"></i>
      </b-row>
      <b-row>
        <i class="far fa-thumbs-up fa-2x ml-3"><span class="h3">좋아요{{likeCnt}}</span></i>
        <i class="far fa-thumbs-down fa-2x ml-3"><span class="h3">싫어요</span></i>
      </b-row>
    </b-container>

    <b-container style="background: white; border-radius: 1rem;" class="py-4 mt-4">
      <b-row>
        <h2 class="ml-4 mb-0">댓글(숫자)</h2>
      </b-row>
      <hr class="my-2">
      <!-- 댓글 폼 필요 -->
      
      <CommentForm/>      
      <hr class="my-2">
      <CommentList/>      
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
      likeCnt: '',
    }
  },
  mounted() {
    axios.get(`${this.$store.getters.getServer}/freeboard/posting/${this.$route.query.pid}`)
    .then((res) => {
      console.log(res.data.posting)
      this.name = res.data.name
      this.classifier = res.data.posting.classifier
      this.title = res.data.posting.title
      this.content = res.data.posting.content
      this.createDate = res.data.posting.createDate
      this.modifyDate = res.data.posting.modifyDate
      this.uid = res.data.posting.uid
      this.likeCnt = res.data.posting.likeCnt
    })
    // fetch(`데이터가 담겨이쓴 주소`).then((res) => {
    //   if (res.ok) {
    //     return res.json();
    //   } else {
    //     throw res;
    //   }
    // }).then(data => {
    //   this.content = data;
    // })
  },
  methods: {
    updateBoard() {
      const pid = this.$route.query.pid
      this.$router.push({name: 'update_board', query: { pid }})
    },
  },
}
</script>

<style>

</style>