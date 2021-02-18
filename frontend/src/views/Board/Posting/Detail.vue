<template>
  <b-container fluid="lg">
    <b-row class="brw">
      <b-col class="bcl">
        title
      </b-col>
      <b-col>
        {{ posting.title }}
      </b-col>
    </b-row>
    <b-row class="brw">
      <b-col class="bcl">
        tag
      </b-col>
      <b-col>
        {{ posting.tag }}
      </b-col>
    </b-row>
    <b-row class="brw">
      <b-col class="bcl">
        name
      </b-col>
      <b-col>
        {{ posting.name }}
      </b-col>
    </b-row>
    <b-row class="brw">
      <b-col class="bcl">
        like
      </b-col>
      <b-col>
        {{ posting.likeCnt }}
      </b-col>
    </b-row>
    <b-row rows="2">
      {{ posting.content }}
    </b-row>
  </b-container>
</template>
<script>
export default {
  data() {
    return {
      desc: "DESC",
      posting: []
    };
  },
  props: ["pid"],
  watch: {
    pid(pid, old) {
      this.getPosting();
    }
  },
  methods: {
    getPosting() {
      const adr = `${this.$store.getters.getBoardServer}/freeboard/posting/${this.pid}`;
      axios
        .get(adr)
        .then(response => {
          this.posting = response.data.posting;
          this.posting.name = response.data.name;
        })
        .catch(response => {
          console.log("FAIL", response);
        });
      axios
        .get(adr)
        .then(response => {
          this.posting = response.data.posting;
          this.posting.name = response.data.name;
        })
        .catch(response => {
          console.log("FAIL", response);
        });
    }
  },
  created() {
    this.getPosting();
  }
};
</script>
<style>
.brw {
  border-bottom: 5px white solid;
}
.bcl {
  width: 30%;
  /* border-right: 1px black solid; */
}
</style>
113d genconsole.log(res);
            t
      if (this.rmid === undefined) {
        console.log("확인");
        this.$router.push({ name: "나의 로드맵" });
        reurn;    }
  () {
      if this.CUMode == 1                                
    }console.log('####################')
      console.log(ths.roadmapname.length)
      i
      console.log(recommend);