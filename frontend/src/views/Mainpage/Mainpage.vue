<template>
  <div>
    <b-container>
      <b-carousel
        id="carousel-1"
        v-model="slide"
        :interval="10000"
        controls
        indicators
        background="#ababab"
        img-width="1024"
        img-height="480"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <!-- 인기 로드맵 -->
        <b-carousel-slide>
          <template #img>
            <div style="background-color: black">


            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=183"
              alt="image slot"
            >
            </div>
          </template>
          <h1 style="color: white;">Highly mentioned Roadmap</h1>
        </b-carousel-slide>

        <!-- 인기 게시글 -->
        <b-carousel-slide>
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=186"
              alt="image slot"
            >
          </template>
          <h1 style="color: white;">Popular post</h1>
        </b-carousel-slide>

        <!-- 오피셜 로드맵(싸피, 개발자) -->
        <b-carousel-slide v-for="(roadmap, idx) in roadmapshares" :key="idx">
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              :src=pictureList[idx]
              alt="image slot"
            >
          </template>
          <h1 style="color: white;">{{ roadmap.title }}</h1>
        </b-carousel-slide>
      </b-carousel>

    </b-container>


  </div>
</template>
<script>
export default {
  data() {
    return {
      roadmapshares: [],
      postings: [],
      slide: 0,
      sliding: null,
      pictureList: [
        "https://picsum.photos/1024/480/?image=183",
        "https://picsum.photos/1024/480/?image=186",
        "https://picsum.photos/1024/480/?image=223",
        "https://picsum.photos/1024/480/?image=244",
        "https://picsum.photos/1024/480/?image=349",
      ],
    };
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    },
    getRoadmapshares: function() {
      axios
        .get(`${this.$store.getters.getRoadmapServer}/roadmapshare/main`)
        .then(res => {
          console.log(res);
          this.roadmapshares = res.data["roadmapshares"]
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
          this.postings = res.data["postings"]
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
