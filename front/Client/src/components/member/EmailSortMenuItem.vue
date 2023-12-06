<template>
    <div class="text-center" style="width: 120px;">
      <v-select v-model="location" :items="locations" label="도메인"></v-select>
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
      import { ref, watch, defineEmits } from "vue";
  
      const items = [
        { title: "Click Me" },
        { title: "Click Me" },
        { title: "Click Me" },
        { title: "Click Me 2" },
      ]
  
      const locations = ["선택", "ssafy.com", "naver.com", "gmail.com"]
      const location = ref("선택");

      // 부모에게 이벤트를 발생시키기 위한 emits 설정
    const emit = defineEmits(["message"]);

    const sendMessageToParent = () => {
    // 자식에서 부모로 메시지를 보냄
    console.log(location.value)
    emit("message", location.value);
    };

    // location 값이 변경될 때마다 sendMessageToParent 호출
    watch(() => location.value, () => {
        sendMessageToParent();
    });
  </script>
  