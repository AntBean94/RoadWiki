<template>
  <div class='d-flex flex-column'>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-default">
    </base-header>

    <div class="p-4 bg-secondary">
      <b-input 
        placeholder="제목" 
        class="form-control-alternative"
        v-model="title"
      />
    </div>

    <!--부트스트랩 드롭다운-->
    <div>
      <b-dropdown id="dropdown-1" :text="selectedRoadmapName" class="m-md-2">
        <b-dropdown-item 
          @click="selectRoadmapToShare(item.rmid, item.name)"
          v-for="(item, index) in userRoadmapList" 
          :key="index">{{ item.name }} | {{ item.createDate }}
        </b-dropdown-item>
      </b-dropdown>
    </div>

    <b-button
      @click="createRoadmapToShare()"
      class="mt-3 mx-4"
      variant="default"
    >
      저장
    </b-button>

  </div>
</template>

<script>
export default {
  name: 'Editor',
  props: {
  },
  data() {
    return {
      initialText: '내용을 입력해주세요',
      title: '',
      userRoadmapList: [],
      roadmapData: "",
      rmid: "",
      selectedRoadmapName: "선택해 로드맵!",
    };
  },
  mounted() {
    console.log('createMode는 ', this.createMode)
    const uid = String(this.$store.getters.getUid)
    axios.get(`${this.$store.getters.getServer}/roadmap/list/${uid}`)
      .then((res) => {
        if(res.data.msg == 'success') {
          // 유저의 roadmaplist
          if(res.data['roadmaps'].length){
            this.userRoadmapList = res.data['roadmaps'];
          }
        } else {
          alert("데이터 로드에 실패했습니다.")
        }
      }).catch((e) =>{
        console.log(e);
        alert("axios 오류")
      });
  },
  methods: {
    selectRoadmapToShare(rmid, name) {
      // 1. 로드맵 데이터 호출
      axios.get(`${this.$store.getters.getServer}/roadmap/get/${rmid}`)
      .then((res) => {
        if(res.data.msg == 'success'){
        this.roadmapData = JSON.parse(res.data['roadmaps'].tmp);
        }else{
          console.log(res);
          alert("데이터 로드에 실패했습니다.");
        }
      }).catch((e) =>{
        console.log(e)
        alert("axios 오류")
      });
      // 2. uid 호출
      const uid = this.$store.getters.getUid
      // 3. title 호출
      const title = this.title
      this.rmid = rmid

      // 4. item.name => this.에 보관
      this.selectedRoadmapName = name
    },
    createRoadmapToShare() {
      if (this.rmid && this.title) {
        axios.post(`${this.$store.getters.getServer}/roadmapshare/insert`, {
          rmid: this.rmid,
          uid: this.$store.getters.getUid,
          title : this.title,
        })
        .then(res => {
          console.log(res)
        })
        .catch(err => {
          console.error(err)
        })
        this.$router.push({ name: 'shareboard' })
      } else if (!this.title) {
        alert("글의 제목을 입력해주세요.")
      } else if (!this.rmid) {
        alert("공유할 로드맵을 선택해주세요.")
      }
    },
  },
}
</script>

<style>

</style>