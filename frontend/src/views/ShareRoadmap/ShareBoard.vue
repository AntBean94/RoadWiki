<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>
    <div>
      <b-pagination-nav
        :link-gen="linkGen"
        :number-of-pages="10"
        use-router
      ></b-pagination-nav>
      <b-button variant="primary" class="mt-2" @click="createBoard">새 글 작성</b-button>

      <b-dropdown id="dropdown-1" :text="category" class="m-md-2">
        <b-dropdown-item
          v-for="(item, idx) in searchCategory"
          :key="idx"
          @click="selectCategory(item)"
          >{{ item }}</b-dropdown-item
        >
      </b-dropdown>
      <b-form-input v-model="inputtext" placeholder="검색어를 입력해주세요."></b-form-input>
      <b-button @click="searchSharedRoadmap">검색하기</b-button>

      <RoadmapList
        v-if="shareList"
        :rmlist="shareList"
        :unlist="usernameList"
      />
    </div>
    <!-- <b-container>
      <b-row align-h="end">
        <b-button variant="primary" class="mt-2" @click="detailBoard">detail board</b-button>
      </b-row>
    </b-container> -->
  </div>
</template>

<script>
import RoadmapList from "@/components/Roadmap/ShareRoadmap/RoadmapList.vue";
export default {
  created() {
    this.getSharedRoadmap();
  },
  components: {
    RoadmapList
  },
  data() {
    return {
      shareList: [],
      usernameList: [],
      searchCategory: ["title", "name"],
      category: "title",
      inputtext: ""
    };
  },
  methods: {
    createBoard() {
      // props로 일반글 작성인지 로드맵 공유인지 구분 => createboard에서 조건에따라 나눔
      this.$router.push({ name: "create_board", params: { createMode: 1 } });
    },
    getSharedRoadmap() {
      axios
        .get(`${this.$store.getters.getServer}/roadmapshare/get`)
        .then(response => {
          this.shareList = response.data.roadmapshares;
          this.usernameList = response.data.username;
        })
        .catch(e => {
          console.log(e);
        });
    },
    linkGen(pageNum) {
      return pageNum === 1 ? "?" : `?page=${pageNum}`;
    },
    searchSharedRoadmap() {
      console.log(this.category, this.inputtext)
      axios
        .get(
          `${this.$store.getters.getServer}/roadmapshare/get/${this.category}/${this.inputtext}`
        )
        .then(response => {
          console.log(response);
          this.shareList = response.data.roadmapshares;
          this.usernameList = response.data.username;
        })
        .catch(e => {
          console.log(e);
        });
    },
    selectCategory(item) {
      this.category = item;
    }
  }
};
</script>

<style></style>
