<template>
  <b-container> </b-container>
</template>
<script>
export default {
  data() {
    return {
      desc: "DESC",
      frame: [
        {
          desc: "ID",
          data: ""
        },
        {
          desc: "TITLE",
          data: ""
        },
        {
          desc: "NAME",
          data: ""
        },
        {
          desc: "TIME",
          data: ""
        },
        {
          desc: "CONTENT",
          data: ""
        },
        {
          desc: "LIKE",
          data: ""
        }
      ]
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
          var posting = response.data.posting;
          this.frame[0].data = posting.pid;
          this.frame[1].data = posting.title;
          this.frame[2].data = response.data.name;
          this.frame[3].data = posting.createDate;
          this.frame[4].data = posting.content;
          this.frame[5].data = posting.likeCnt;
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
