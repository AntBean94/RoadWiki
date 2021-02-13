<template>
<div>
  <div style="display: block">
    <div v-for="(rm, idx) in rmlist" :key="idx">
      <RoadmapListItem  id="rmli" :roadmap="rm" :username="unlist[idx]" />
    </div>
  </div>
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
  created() {
  },
}
</script>

<style>
#rmli{
  float:left;
  display: inline-block;
  width: 250px;
  height: 400px;
  margin: 25px;
  overflow: overflow;
}
</style>