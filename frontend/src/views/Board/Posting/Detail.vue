<template>
  <b-container fluid="sm">
    <b-row>
      <b-col cols="3">
        title
      </b-col>
      <b-col cols="9">
        {{ posting.title }}
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="3">
        tag
      </b-col>
      <b-col cols="9">
        {{ posting.tag }}
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="3">
        name
      </b-col>
      <b-col cols="9">
        {{ posting.name }}
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="3">
        like
      </b-col>
      <b-col cols="9">
        {{ posting.like }}
      </b-col>
    </b-row>
    <b-row>
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
    pid: function(pid, old) {
      this.getPosting();
    }
  },
  methods: {
    getPosting() {
      var adr = `${this.$store.getters.getServer}/freeboard/posting/${this.pid}`;
      axios
        .get(adr)
        .then(response => {
          console.log(response);
          console.log(response.data);
          this.posting = response.data.posting;
          this.posting.name = response.data.name;
          console.log(this.posting);
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
<style></style>
