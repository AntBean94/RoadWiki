<template>
  <div>
    <header>
      <span style="color:red; margin: 50px; font-size:30px"
        >########## main ###########</span
      >
    </header>
    <div>
      <li v-for="(roadmapshare, idx) in roadmapshares" :key="idx">
        {{ roadmapshare.title }}
      </li>
    </div>
    <div>
      <li v-for="(posting, idx) in postings" :key="idx">
        {{ positng.title }}
      </li>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      roadmapshares: [],
      postings: []
    };
  },
  methods: {
    getRoadmapshares: function() {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmapshare/main`)
        .then(res => {
          console.log(res);
        })
        .catch(res => {
          console.log(res);
        });
    },
    getPostings: function() {
      axios
        .get(`${this.$store.getters.getBoardServer}/freeboard/main`)
        .then(res => {
          console.log(res);
        })
        .catch(res => {
          console.log(res);
        });
    }
  },
  created() {
    this.getRoadmapshares();
    this.getPostings();
  }
};
</script>
