<template>
  <div>
    <!-- <b-container v-for="(comment, idx) in comments" :key="idx"> -->
      <!-- 이번 댓글은 {{ comment }} -->
      <b-row>
        <b-col cols="8" align-self="center">
          <!-- 닉네임 정보 가져오기 -->
          [img] 작성자 : {{ nickname }}
        </b-col>
        <b-col>
          <h5>
            {{ comment.createDate }}
          </h5>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="8">
          <p class="px-3">
            {{ comment.content }}
          </p>
        </b-col>
        <b-col align-h="end" class="my-2">
          <i class="far fa-thumbs-up fa-2x ml-3" v-if="!like" @click="clickLike"><span class="h3 ml-1">{{ comment.likeCnt }}</span></i>
          <i class="fas fa-thumbs-up fa-2x ml-3" v-if="like" @click="cancelLike"><span class="h3 ml-1">{{ comment.likeCnt }}</span></i>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="8" class="mb-2">
          <b-button size="sm" @click="makeRecomment">
            <i class="fas fa-reply"></i>
            답글
          </b-button>
        </b-col>
        <!-- 아이콘 가운데정렬 -->
        <!-- 현재 로그인 된 uid와 댓글 uid가 같은 경우 보여주기 -->
        <b-col align-h="end" class="my-2" v-if="isCommentWritter">
          <i class="far fa-trash-alt fa-lg mr-3" style="color: tomato"></i>
          <i class="far fa-edit fa-lg mr-3" style="color: Dodgerblue"></i>
        </b-col>
      </b-row>
      <b-container v-for="(recomment, idx) in recomments" :key="idx">
        <RecommentList :recomment="recomment"/>      
      </b-container>
      <!-- <RecommentList :recomments="recomments" v-if="recomments.length > 0" /> -->
      <RecommentForm v-show="recomment"/> 
    <!-- </b-container> -->
  </div>
</template>

<script>
import RecommentForm from '@/components/Board/RecommentForm.vue';
import RecommentList from '@/components/Board/RecommentList.vue';

export default {
  name: 'commentList',
  components: {
    RecommentForm,
    RecommentList,
  },
  data() {
    return {
      recomment: false,
      like: false,
      uid: '',
      nickname: '',
      likeCnt: '',
      isCommentWritter: false,
    }
  },
  props: ['comment', 'recomments'],
  created() {
    axios.get(`${this.$store.getters.getServer}/user/name/${this.comment.uid}`)
    .then((res) => {
      this.nickname = res.data.name
    })
    .catch((err) => {
      console.log(err)
    })

    if (this.comment.uid === this.$store.getters.getUid) {
      this.isCommentWritter = true
    } else {
      this.isCommentWritter = false
    }
  },
  methods: {
    // 대댓글 작성 이후 recomment다시 false로 바꿔주기
    makeRecomment() {
      if (this.recomment) {
        this.recomment = false
        // 하하하 하이 하하이 하늘 하이라는뜻ヾ(•ω•`)o
      } else {
        this.recomment = true
      }
    },
    clickLike() {
      this.like = true
      this.comment.likeCnt ++
    },
    cancelLike() {
      this.like = false
      this.comment.likeCnt --
    },
  },
}
</script>

<style>

</style>