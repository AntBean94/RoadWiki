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
        <!-- Text slides with image -->
        <b-carousel-slide
          caption="First slide"
          text="Nulla vitae elit libero, a pharetra augue mollis interdum."
          img-src="https://picsum.photos/1024/480/?image=52"
        ></b-carousel-slide>

        <!-- Slides with custom text -->
        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
          <h1 style="color: white;">Hello world!</h1>
        </b-carousel-slide>

        <!-- Slides with image only -->
        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58"></b-carousel-slide>

        <!-- Slides with img slot -->
        <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
        <b-carousel-slide>
          <template #img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              height="480"
              src="https://picsum.photos/1024/480/?image=55"
              alt="image slot"
            >
          </template>
        </b-carousel-slide>

        <!-- Slide with blank fluid image to maintain slide aspect ratio -->
        <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunt
            a tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum.
          </p>
        </b-carousel-slide>
      </b-carousel>

    </b-container>




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
      postings: [],
      slide: 0,
      sliding: null,
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
