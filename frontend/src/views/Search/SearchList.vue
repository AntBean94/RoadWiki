<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>

    <b-container style="background: white; border-radius: 1rem;" class="py-4 mt-4">
      <h1>
        "{{ $route.query.searchKeyword }}"검색 결과 페이지입니다.
      </h1>
      <hr class="my-2">
      <h2>유저</h2>
      <ul v-for="(user, idx) in userlist" :key="idx">
        <router-link :to="{name: 'profile', query: {profileId: `${user.uid}`}}">
          {{user.name}}
        </router-link>
      </ul>
      <!-- <ul>여기가 검색 결과 나올 공간</ul>
      <ul>여기가 검색 결과 나올 공간</ul>
      <ul>여기가 검색 결과 나올 공간</ul> -->
      <hr class="my-2">
      <h2>게시글</h2>
      <ul v-for="(posting, index) in postinglist" :key="index">
        {{posting.title}}
      </ul>
      <ul>여기가 검색 결과 나올 공간</ul>
      <ul>여기가 검색 결과 나올 공간</ul>
      <ul>여기가 검색 결과 나올 공간</ul>
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'searchlist',
  data() {
    return {
      content: '',
      userlist: [],
      postinglist: [],
    }
  },
  methods: {
    sendWord() {
      axios.get(`${this.$store.getters.getServer}/search/list/${this.content}`)
      .then((res) => {
        this.content = ''
        console.log()
        console.log(res.data)
        this.userlist = res.data.user
        this.postinglist = res.data.posting
      })
      .catch((err) => {
        console.log(err)
      })
    },
  },
  created() {
    this.content = this.$route.query.searchKeyword
    console.log(this.content)
    this.sendWord()
  },
}
</script>

<style>

</style>