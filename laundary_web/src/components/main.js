import React, { useEffect } from "react";
import ReactDOM from 'react-dom';
import { Grid, Container } from "@material-ui/core";
import "./style/Main.css";
import img1 from "./img/img4.png";
import img2 from "./img/img5.png";
import img3 from "./img/img3.png";
import img6 from "./img/img6.png";
import img5 from "./img/img5.png";
import img7 from "./img/img7.png";
import img8 from "./img/img8.png";
import phone from "./img/phone.png";
import bag from "./img/bag.png";
import truck from "./img/truck.png";
import pop from "./img/pop.png";
import review from "./img/review.png";
import user from "./img/user-main.png";
import review2 from "./img/review2.png";
import login from "./img/login.png";
import order from "./img/order.png";
import name__main from "./img/name__main.png";


const Main = () => { 

		return (
			<Grid xs={12} className="main">
			  <Grid>
					<img className="Main__img" src={img5} />
			  </Grid>

			  <Container>
				  <Grid className="mainContent">
						<p>
							세탁품질
						</p>
						<p>
							타협없는
						</p>
						<p>
							고퀄리티
						</p>
					</Grid>
					<Grid className="wrapper">
						<p className = "laudaryContent__decription" style={{textAlign:"center", width:"50%"}}> 
						COVID-19(코로나바이러스감염증)과 IT 기술 향상에 따라 최근 언택트 시대와 산업이 새로이 도래하고 있습니다. 최근 대형 세탁 산업이 새로 생겨나면서 소상공인 세탁 업계의 소비 감소 및 고령화가 발생하고 있습니다. 저희 세탁이는 세탁 산업 소상공인의 소비 감소와 언택트 산업, 배달 업계의 호황 접목을 통해 기존에 없던 소상공인 세탁 업계의  소비 향상을 위한 세탁 서비스 애플리케이션을 개발하였습니다.
						</p>
					</Grid>
						<br/>

						<section className="name__post__wrapper">
							<article className="name__post">
								<img src={name__main} alt="세탁이 메인 로고"/>

								<div className="name__post__content__text">
								  <p>언제 어디서나</p>
									<p>세탁은 세탁이에서</p>
								</div>

								<img src="">
								</img>
								
							</article>
						</section>

					<Grid>
						<p className = "laudaryContent" style={{textAlign:"left"}}> 
						수백만 점을 세탁해도 고객은 단 한 점의 옷으로 평가하기에, 세탁이는 허상을 판매하지 않습니다. 출혈경쟁의 원가부담을 값싼 노동력과 저급한 원료로 대체하지 않습니다. 품질과 직결되는 수고로운 일에 기교를 부리지 않습니다. 오직 고객과 세탁에 집중합니다.
						</p>
						<br/>
					</Grid>


					<section className="img__Wrapper" >
						<article className="img__content">
							<img src = {img8} style={{width:"100%" , height:"100%"}}/>
						</article>

						<article className="img__content">
							<img src = {img7} style={{width:"100%"}}/>
						</article>

						<article className="img__content">
							<img src = {img6} style={{width:"100%" }}/>
						</article>

					</section>

					<Grid className="bottomContent">

						<Grid className="bottomHeader">
							<p>
								당신이 잠든 새벽에 
							</p>
							<p>
								미션 클리어
							</p>
						</Grid>

						<br/>

						<Grid className="bottomMain">
							<p>
							더이상 세탁에 시간 쏟지 마세요. 터치 한 번이면 세탁 끝. 
							세탁이 요원이 모두가 잠든 새벽에 깨끗한 세탁물을 문 앞까지 배송합니다. 
							모든 세탁물의 사진이 첨부된 내역서와 간편결제, 
							비대면 새벽배송으로 삶의 질을 수직 상승시켜 줄 세탁이를 지금 바로 만나보세요.
							</p>
						</Grid>
					</Grid>

					<Grid className="app__wrapper">
						<article className="app__img">
							<img className="login__img" src={login} alt="로그인" />
							<img className="user__img" src={user} alt="user" />
							<img className="order__img" src={order} alt="order"/>
							<img className="review" src={review} alt="review"/>
							<img className="review2" src={review2} alt="review2"/>
						</article>
					</Grid>
			  </Container>

			</Grid>
		)
	}
	
export default Main;