<template>
  <div class="">
    <base-input>
      <textarea 
        class="form-control" 
        rows="3" 
        placeholder="댓글을 입력해주세요"
        v-model="content"
      >
      </textarea>
      <b-row align-h="end" class="mt-1">
        <b-button variant="provence" class="mt-2 mr-3" @click="sendComment">댓글 작성</b-button>
      </b-row>
    </base-input>
  </div>
</template>

<script>
export default {
  name: 'commentform',
  data() {
    return {
      content: '',
    }
  },
  props: ['pid'],
  methods: {
    sendComment() {
      let posting = {
        'pid': this.pid,
        'uid': this.$store.getters.getUid,
        'content': this.content,
      }
      if (posting.content.length > 0) {
        axios
        .post(`${this.$store.getters.getServer}/freeboard/comment`, posting)
        .then((res) => {
          this.$emit('sendComment')
          this.content = ''
        })
        .catch((err) => {
          console.log(err)
        })
      } else {
        alert('댓글을 작성해주세요')
      }
    },
  },
}
</script>

<style>

</style>