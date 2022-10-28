<template>
  <div  style="background-color:aquamarine">
    <h3> Hi</h3>
  </div>
</template>
<script>
export default {
  data () {
    return {
      content: [],
      project_name: this.$store.state.project_name
    }
  },
  created () {
    console.log('hi')
  },
  methods () {

  }
}
</script>
<style>

</style>
