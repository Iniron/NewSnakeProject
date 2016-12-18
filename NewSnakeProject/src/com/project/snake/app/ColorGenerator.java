package com.project.snake.app;

import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorGenerator {
	
	LinkedList<Paint> colorList;
	
	public ColorGenerator() {
		colorList = new LinkedList<>();
	}
	
	public void ColorCreate(){
		colorList.add(Color.web("#f2b233"));	//주황
		colorList.add(Color.web("#8342bd"));	//보라
		colorList.add(Color.web("#e388cc"));	//분홍
		colorList.add(Color.web("#ed6051"));	//빨강
		colorList.add(Color.web("#2eb094"));	//초록
	}
	
	public Paint getRandomColor(){
		return colorList.get((int)(Math.random()*colorList.size()));
	}
	
	//푸드를 랜덤으로 생성할때 칼라도 같이생성
	//출력할때만 쓰인다.
}

//#3bbdc4 / 60, 187, 194 판넬 하늘색
//#2cb8d1 / 44, 184, 209 하늘색
//#f2b233 / 240, 174, 53 주황색
//#8342bd / 129, 66, 189 보라색
//#e388cc / 227, 136, 203 분홍색
//#ed6051 / 235, 95, 82 빨간색
//#2eb094 / 48, 176, 148 초록색