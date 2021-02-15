<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>
    <div>
      <b-button variant="primary" class="mt-2" @click="createBoard"
        >새 글 작성</b-button
      >

      <b-dropdown id="dropdown-1" :text="category" class="m-md-2">
        <b-dropdown-item
          v-for="(item, idx) in searchCategory"
          :key="idx"
          @click="selectCategory(item)"
          >{{ item }}</b-dropdown-item
        >
      </b-dropdown>
      <b-form-input
        v-model="inputtext"
        placeholder="검색어를 입력해주세요."
      ></b-form-input>
      <b-button @click="searchSharedRoadmap">검색하기</b-button>

      <RoadmapList
        class="row"
        id="roadmaplist"
        v-if="shareList"
        :rmlist="viewShareList"
        :unlist="usernameList"
      />
    </div>

    <b-pagination
      v-model="currentPage"
      :per-page="10"
      :total-rows="totalPageNum"
      aria-controls="mhtable"
      align="center"
    ></b-pagination>

  </div>
</template>

<script>
import RoadmapList from "@/components/Roadmap/ShareRoadmap/RoadmapList.vue";
export default {
  mounted() {
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
      inputtext: "",
      currentPage : 1,
      totalPageNum: 3,
    };
  },
  computed: {
    viewShareList: function () {
      return this.shareList.slice((this.currentPage-1) * 10, (this.currentPage-1) * 10 + 10 )
    }
  },
  methods: {
    createBoard() {
      this.$router.push({ name: "create_share_board" });
    },
    getSharedRoadmap() {
      axios
        .get(`${this.$store.getters.getServer}/roadmapshare/get`)
        .then(response => {
          this.shareList = response.data.roadmapshares;
          this.usernameList = response.data.username;
          this.totalPageNum = response.data.roadmapshares.length
          this.currentPage = 1
        })
        .catch(e => {
          console.log(e);
        });
    },
    searchSharedRoadmap() {
      axios
        .get(
          `${this.$store.getters.getServer}/roadmapshare/get/${this.category}/${this.inputtext}`
        )
        .then(response => {
          this.shareList = response.data.roadmapshares;
          this.usernameList = response.data.username;
          this.totalPageNum = response.data.roadmapshares.length
          this.currentPage = 1
        })
        .catch(e => {
          console.log(e);
        });
    },
    selectCategory(item) {
      this.category = item;
    }
  },
};
</script>

<style>
#roadmaplist {
  display: block;
}
</style>
