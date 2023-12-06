<template>
  <div class="text-center" style="width: 120px;">
    <v-select v-model="location" :items="locations" label="정렬"></v-select>
    <v-menu :location="location">
      <v-list>
        <v-list-item v-for="(item, index) in items" :key="index">
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>

</template>

<script setup>
    import { ref } from "vue";

    const items = [
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me" },
      { title: "Click Me 2" },
    ]

    const locations = ["최신순", "조회수순"]
    const location = ref("최신순")
</script>
