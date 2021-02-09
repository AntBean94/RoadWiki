<template>
  <div>
    <b-row>
      <b-col cols="8" align-self="center">
        [img] 작성자 : {{ nickname }}
      </b-col>
      <b-col>
        <h5>
          {{ comment.createDate }}
        </h5>
      </b-col>
    </b-row>
    <b-row v-if="!isUpdate">
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
    <b-row v-if="isUpdate">
      <base-input>
        <textarea 
          class="form-control" 
          rows="3" 
          v-model="comment.content"
          autofocus
          cols="200"
        >
        </textarea>
        <b-row align-h="end">
          <b-button variant="default" class="mt-2 mr-3" @click="sendComment">수정 완료</b-button>
        </b-row>
      </base-input>
    </b-row>
    <b-row v-if="!isUpdate">
      <b-col cols="8" class="mb-2">
        <b-button size="sm" @click="makeRecomment">
          <i class="fas fa-reply"></i>
          답글
        </b-button>
      </b-col>
      <!-- 아이콘 가운데정렬 -->
      <b-col align-h="end" class="my-2" v-if="isCommentWriter">
        <i class="far fa-trash-alt fa-lg mr-3" style="color: tomato" @click="deleteComment"></i>
        <i class="far fa-edit fa-lg mr-3" style="color: Dodgerblue" @click="updateComment"></i>
      </b-col>
    </b-row>
    <b-container v-for="(recomment, idx) in recomments" :key="idx">
      <RecommentList :recomment="recomment"/>      
    </b-container>
    <RecommentForm v-show="recomment" :cid="comment.cid" @sendRecomment="sendRecomment"/> 
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
      isCommentWriter: false,
      isUpdate: false,
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
      this.isCommentWriter = true
    } else {
      this.isCommentWriter = false
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
    sendRecomment() {
      this.$emit('sendRecomment')
    },
    deleteComment() {
      axios.delete(`${this.$store.getters.getServer}/freeboard/comment/${this.comment.cid}`)
      .then(() => {
        alert('댓글이 삭제되었습니다.')
        this.$emit('sendRecomment')
      })
    },
    sendComment() {
      let comment = {
        'cid': this.comment.cid,
        'content': this.comment.content,
      }
      axios.put(`${this.$store.getters.getServer}/freeboard/comment`, comment)
      .then(() => {
        alert('수정되었습니다.')
        this.$emit('sendRecomment')
        this.isUpdate = false
      })
    },
    updateComment() {
      this.isUpdate = true
    },
  },
}
</script>

<style>

</style>