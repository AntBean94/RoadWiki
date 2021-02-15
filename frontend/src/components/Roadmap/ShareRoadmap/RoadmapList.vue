<template>
  <div class ="d-flex justify-content-center">
      <RoadmapListItem 
        v-for="(rm, idx) in rmlist" 
        :key="idx" 
        id="rmli" 
        :roadmap="rm" 
        :username="unlist[idx]" 
      />
  </div>
</template>

<script>
import RoadmapListItem from '@/components/Roadmap/ShareRoadmap/RoadmapListItem'
export default {
  props: {
      rmlist: Array,
      unlist: Array,
    },
  components: {
    RoadmapListItem
  },
  beforeMount(){
  },
  created() {
  },
}
</script>

<style>
#rmli {
  width: 250px;
  height: 400px;
  margin: 25px; 
  overflow: hidden;
}
</style>