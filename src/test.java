
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int currentPage = 3;
		int modVal = 0;
		int startPage = 0;
		int endPage = 0;
		
		modVal = currentPage % 5;
		startPage = currentPage / 5 * 5 + 1;
		if (modVal == 0) startPage -=5;
		
		endPage = startPage + 4;
		
		System.out.println("현재 페이지 "+currentPage);
		System.out.println("모드 밸 "+modVal);
		System.out.println("시작 페이지 "+startPage);
		System.out.println("끝 페이지 "+endPage);

	}

}
