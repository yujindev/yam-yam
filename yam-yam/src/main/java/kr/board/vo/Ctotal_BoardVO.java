package kr.board.vo;
import java.sql.Date;

public class Ctotal_BoardVO {
	private long board_num;
	private String board_title;
	private String board_name;
	private Date board_date; 
	private long mem_num;

	public long getBoard_num() {
		return board_num;
	}
	public void setBoard_num(long board_num) {
		this.board_num = board_num;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public Date getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Date board_date) {
		this.board_date = board_date;
	}
	public long getMem_num() {
		return mem_num;
	}
	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}

	public String getTableUrl() {
		switch(board_name){
		case "comm_talk" : return "ctalk_board/ctalk";
		case "comm_menu" : return "cmenu_board/cmenu";
		case "comm_bob" : return "cbob_board/cbob";
		case "comm_mov" : return "cmov_board/cmov";
		case "comm_zone" : return "czone_board/czone";
		default : return "";
		}
	}
	public String getTableUrlNum() {
		switch(board_name){
		case "comm_talk" : return "ctalk_num";
		case "comm_menu" : return "cmenu_num";
		case "comm_bob" : return "cbob_num";
		case "comm_mov" : return "cmov_num";
		case "comm_zone" : return "czone_num";
		default : return "";
		}
	}
	public String getCategory() {
		switch(board_name){
		case "comm_talk" : return "잡담";
		case "comm_menu" : return "메뉴추천";
		case "comm_bob" : return "밥친구 찾기";
		case "comm_mov" : return "영상추천";
		case "comm_zone" : return "도시락존";
		default : return "";
		}
	}
}