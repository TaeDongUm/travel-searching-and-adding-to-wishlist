<template>
  <!-- Page Content-->
		<div class="container px-4 px-lg-5">
			<!-- Heading Row-->
			<div class="row gx-4 gx-lg-5 align-items-center my-5">
				<div class="col-lg-7">
					<img class="img-fluid rounded mb-4 mb-lg-0" src="@/assets/thumbnail.png" alt="..." />
				</div>
				<div class="col-lg-5">
					<h1 class="font-weight-regular">여행은 항상 Enjoy하게✨</h1>
					<p>
						<br>여행지 정보부터 실시간 지도 서비스, 그리고 여행 친구를 찾을 수 있는 게시판까지, 저희 EnjoyTrip은 여행을 더욱 특별하게 만들어주는 다양한 기능을 제공합니다. 
						<br> 자세한 관광지 정보와 놀라운 사진들을 탐험하며, 지도 서비스를 활용하여 목적지를 손쉽게 찾아가세요. 게시판을 통해 여행 친구를 만나고 새로운 경험을 나누며 여행을 더욱 풍부하게 즐길 수 있는 특별한 여행 커뮤니티입니다. 
						<br>여행의 즐거움을 함께 나누고 싶다면 지금 바로 가입하세요!
						<br> 
					</p>
					<a class="btn btn-primary"><router-link class="nav-link" :to="{ name: 'kakaoMap' }">관광지를 둘러 보세요!</router-link></a>
				</div>
			</div>
			<!-- Call to Action-->
			<div class="card bg-dark my-5 text-left">
				<div class="card-body row">
					<h3 class="text-white m-0 po-5 col-md-3 mx-1 my-1 py-5">
						엔조이트립과 함께<br>여행을<br>더 즐겁게! 
					</h3>
					<div class="card-body rounded col-md-2 mx-1 my-1">
						인기 게시글 1위
					</div>
					<div class="card-body rounded col-md-2 mx-1 my-1">
						인기 게시글 2위
					</div>
					<div class="card-body rounded col-md-2 mx-1 my-1">
						인기 게시글 3위
					</div>
				</div>
			</div>
			<!-- Content Row-->
			<div class="row gx-4 gx-lg-5">
				<div class="col-md-4 mb-5">
					<div class="card h-100">
						<div class="card-body">
							<h2 class="card-title">Card One</h2>
							<p class="card-text">
								Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem magni quas ex numquam,
								maxime minus quam molestias corporis quod, ea minima accusamus.
							</p>
						</div>
						<div class="card-footer"><a class="btn btn-primary btn-sm" href="#!">More Info</a></div>
					</div>
				</div>
				<div class="col-md-4 mb-5">
					<div class="card h-100">
						<div class="card-body">
							<h2 class="card-title">Card Two</h2>
							<p class="card-text">
								Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quod tenetur ex natus at
								dolorem enim! Nesciunt pariatur voluptatem sunt quam eaque, vel, non in id dolore
								voluptates quos eligendi labore.
							</p>
						</div>
						<div class="card-footer"><a class="btn btn-primary btn-sm" href="#!">More Info</a></div>
					</div>
				</div>
				<div class="col-md-4 mb-5">
					<div class="card h-100">
						<div class="card-body">
							<h2 class="card-title">Card Three</h2>
							<p class="card-text">
								Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem magni quas ex numquam,
								maxime minus quam molestias corporis quod, ea minima accusamus.
							</p>
						</div>
						<div class="card-footer"><a class="btn btn-primary btn-sm" href="#!">More Info</a></div>
					</div>
				</div>
			</div>
		</div>
</template>

<script>
export default {

}
</script>

<style>
.card-body .rounded {
	background-color: #bfd4f5;
}
</style>