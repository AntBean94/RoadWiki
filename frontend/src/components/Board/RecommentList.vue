<template>
  <div class="bg-secondary">
    <hr class="my-2">
    <!-- <b-container v-for="(recomment, idx) in recomments" :key="idx"> -->
      <!-- {{ recomment }} -->
      <b-row>
        <b-col cols="8" align-self="center">
          [img] 작성자 : {{ this.nickname }}
        </b-col>
        <b-col>
          <h5>
            {{ recomment.createDate }}
          </h5>
          <h5>
            {{ recomment.modifyDate }}
            0000년 00월 00일 00:00
          </h5>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="8">
          <p class="px-3">
            {{ recomment.content }}
          </p>
        </b-col>
        <b-col align-h="end" class="my-2">
          <i class="far fa-thumbs-up fa-2x ml-3" v-if="!like" @click="clickLike"><span class="h3 ml-1">{{ recomment.likeCnt }}</span></i>
          <i class="fas fa-thumbs-up fa-2x ml-3" v-if="like" @click="cancelLike"><span class="h3 ml-1">{{ recomment.likeCnt }}</span></i>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="8">
        </b-col>
        <!-- 아이콘 가운데정렬 -->
        <b-col align-h="end" class="my-2" v-if="isRecommentWriter">
          <i class="far fa-trash-alt fa-lg mr-3" style="color: tomato" @click="deleteRecomment"></i>
          <i class="far fa-edit fa-lg mr-3" style="color: Dodgerblue" @click="updateRecomment"></i>
        </b-col>
      </b-row> 
    <!-- </b-container> -->
  </div>
</template>

<script>
export default {
  name: 'commentList',
  components: {
  },
  data() {
    return {
      like: false,
      nickname: '',
      isRecommentWriter: false,
    }
  },
  props: ['recomment'],
  methods: {
    clickLike() {
      this.like = true
      this.recomment.likeCnt ++
    },
    cancelLike() {
      this.like = false
      this.recomment.likeCnt --
    },
    deleteRecomment() {

    },
    updateRecomment() {

    },
  },
  created() {
    axios.get(`${this.$store.getters.getServer}/user/name/${this.recomment.uid}`)
    .then((res) => {
      this.nickname = res.data.name
    })
    .catch((err) => {
      console.log(err)
    })

    if (this.recomment.uid === this.$store.getters.getUid) {
      this.isRecommentWriter = true
    } else {
      this.isRecommentWriter = false
    }
  }
}
</script>

<style>

</style>