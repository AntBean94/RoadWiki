<template>
  <div>
    <base-input>
      <textarea 
        class="form-control" 
        rows="3" 
        placeholder="댓글을 입력해주세요"
        v-model="comment"
      >
      </textarea>
      <b-row align-h="end">
        <b-button variant="default" class="mt-2 mr-3" @click="sendComment">댓글 작성</b-button>
      </b-row>
    </base-input>
  </div>
</template>

<script>
export default {
  name: 'commentform',
  data() {
    return {
      comment: '',
    }
  },
  props: ['pid'],
  methods: {
    sendComment() {
      axios
      .post(`${this.$store.getters.getServer}/freeboard/comment`, 
      {
        'pid': this.pid,
        'uid': this.$store.getters.getUid,
        'content': this.comment,
      })
      .then((res) => {
        alert('댓글이 작성되었습니다.')
      })
      .catch((err) => {
        console.log(err)
      })
    },
  },
}
</script>

<style>

</style>