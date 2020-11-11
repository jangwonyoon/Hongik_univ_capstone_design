import React from "react";
import {
     Grid,
     Button,
     Hidden,
     Box,
     Container,
     createMuiTheme,
     MuiThemeProvider,
	 makeStyles
		} from "@material-ui/core";
import "./style/Header.css";
import name__main from "./img/name__main.png";

const useStyles = makeStyles((theme) => ({
  root:{
	fontFamily: "Noto Sans KR",
  },
  left:{
	fontFamily: "Noto Sans KR, sans-serif",
	color:"#FFFFFF",
	fontSize:"36px",
	fontWeight:"700",
	textDecoration:"none",
  }
}))

const Header = () => {

	const classes  = useStyles();

	/* 링크 모음 */
	const memberLink = "https://www.notion.so/Framework-a0b51c5f08694cde8de57660e53be9f7";
	const ownerLink = "http://edit0.dothome.co.kr/makeweb/web_o_login.php";
	const userLink = "http://edit0.dothome.co.kr/makeweb/web_searching.php";
	
	return(
		<Grid container className = "header">
			<Grid className="header__content">
				<Grid xs={3} className="header__logo">
					<a href="#">
						<img src={name__main} style={{width:"25%"}}/>
					</a>
				</Grid>
				
			<Grid xs={9} className="btn__contents">
				<Button className="right" >
					<a href={ownerLink} >사장님 사이트</a>
				</Button>
				<Button className="right" >
					<a href={memberLink} >멤버 소개</a>
				</Button>
				<Button className="right" >
					<a href={userLink} >검색</a>
				</Button>
			</Grid>
			</Grid>
		</Grid>
	)
}

export default Header;

