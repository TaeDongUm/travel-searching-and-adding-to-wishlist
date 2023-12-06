<script setup>
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import TheFooterBar from "@/components/layout/TheFooterBar.vue";
import { onMounted } from "vue";
import { useAuth } from "./hooks/useAuth";

const { onMountedCallBack } = useAuth()
onMounted(onMountedCallBack)

</script>

<template>
	<body>
		<TheHeadingNavbar></TheHeadingNavbar>
		<router-view></router-view>
		<TheFooterBar></TheFooterBar>

	</body>
</template>

<style scoped></style>
