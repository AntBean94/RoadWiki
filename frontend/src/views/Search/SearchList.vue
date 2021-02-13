<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>

    <b-container
      style="background: white; border-radius: 1rem;"
      class="py-4 mt-4"
    >
      <base-input>
        <textarea
          class="form-control mt-2"
          rows="2"
          placeholder="댓글을 입력해주세요"
          v-model="content"
        >
        </textarea>
        <b-row align-h="end">
          <b-button
            variant="default"
            class="mt-2 mr-3"
            @click="sendWord"
            size="sm"
          >
            등록
          </b-button>
        </b-row>
      </base-input>
      <h1>"{{ $route.query.searchKeyword }}"검색 결과 페이지입니다.</h1>
      <hr class="my-2" />
      <h2>유저</h2>
      <ul v-for="(user, idx) in userlist" :key="idx">
        <router-link
          :to="{ name: 'profile', params: { youruid: `${user.uid}` } }"
        >
          {{ user.name }}
        </router-link>
      </ul>

      <hr class="my-2" />
      <h2>게시글</h2>
      <ul v-for="(posting, index) in postinglist" :key="index">
        {{
          posting.title
        }}
      </ul>
      <hr class="my-2" />
      <h2>공유글</h2>
      <ul v-for="(roadmapdata, index) in roadmapdatalist" :key="index">
        {{
          roadmapdata.title
        }}
      </ul>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "searchlist",
  data() {
    return {
      content: "",
      userlist: [],
      postinglist: [],
      roadmapdatalist: []
    };
  },
  methods: {
    sendWord() {
      axios
        .get(`${this.$store.getters.getServer}/search/list/${this.content}`)
        .then(res => {
          this.content = "";
          this.userlist = res.data.user;
          this.postinglist = res.data.posting;
        })
        .catch(err => {
          console.log(err);
        });

      axios
        .get(
          `${this.$store.getters.getServer}/roadmapshare/get/title/${this.content}`
        )
        .then(res => {
          this.content = "";
          this.roadmapdatalist = res.data.roadmapshares;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.content = this.$route.query.searchKeyword;
    this.sendWord();
  }
};
</script>

<style></style>
