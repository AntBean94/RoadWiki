<template>
  <b-table>
    <b-row v-for="(room, idx) in rooms" :key="idx">
      <b-col>
        {{ room.name }}
      </b-col>
    </b-row>
  </b-table>
</template>
<script>
export default {};
</script>
