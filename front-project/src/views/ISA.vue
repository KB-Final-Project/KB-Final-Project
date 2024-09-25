<template>
  <div class="container text-center">
    <h1 class="d-inline">ISA</h1>
    <p class="d-inline">ISA 하나로 모든 투자를 해결</p>
    <br /><br />

    <div
      class="outer-box"
      style="
        background-color: rgba(135, 140, 67, 0.06);
        border-radius: 30px;
        padding: 30px;
        width: 100%;
        max-width: 1813px;
        margin: auto;
        height: 960px;
        position: relative;
      "
    >
      <div
        style="
          margin-left: 20px;
          text-align: left;
          margin-top: 10px; /* 여전히 아래에 남겨둘 수 있음 */
          background-color: transparent; /* 배경을 투명으로 설정 */
          border-radius: 30px;
          width: 442.5px; /* 피그마 기준 너비의 반으로 조정 */
          height: 713px; /* 기존 높이 유지 */
          padding: 20px; /* 내부 여백 */
          position: relative;
          left: 38px; /* X 위치 */
          top: 40px; /* Y 위치 조정, 더 위로 올리기 */
        "
      >
        <h2
          style="
            margin-left: 0px;
            font-weight: 600;
            font-size: 60px;
            color: #438c74;
          "
        >
          ISA 계좌란?
        </h2>
        <p
          class="description"
          style="font-weight: 400; font-size: 48px; color: black"
        >
          하나의 계좌로 다양한 금융상품을 관리하면서, 절세혜택도 누릴 수 있는
          <span style="font-weight: 600; font-size: 60px; color: #438c74"
            >"통합계좌" </span
          >입니다.
        </p>
        <p style="font-weight: 400; font-size: 20px; color: black">
          채권, 국내상장주식, 펀드, 리츠, ELS, RP 등 다양한 상품을 한 계좌에
          담아 본인의 투자 전략에 맞게 운용하면서 최대 400만원까지 비과세,
          초과분은 9.9% 분리과세 적용을 받을 수 있습니다.
        </p>
      </div>
    </div>

    <br /><br />

    <div
      class="product-background"
      style="
        background-color: rgba(135, 140, 67, 0.06);
        border-radius: 30px;
        padding: 30px;
        width: 100%;
        max-width: 1813px;
        margin: auto;
        position: relative;
        height: 799px; /* 배경 세로 높이 1cm 줄임 */
      "
    >
      <h2 style="margin-left: 20px; text-align: left; margin-bottom: 40px">
        AROUND가 김수대 고객님에게 추천한 ISA
      </h2>

      <div
        class="itemBoxDiv"
        style="
          display: grid;
          grid-template-columns: repeat(3, 1fr);
          gap: 20px;
          justify-items: center;
          margin-bottom: 40px; /* 상품 박스와 페이지네이션 사이의 간격 */
        "
      >
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 1</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 2</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 3</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 4</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 5</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 6</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 7</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 8</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 164px"
        >
          <div class="p-3">상품 9</div>
        </div>
      </div>

      <nav aria-label="Page navigation example" style="margin-bottom: 40px">
        <ul class="pagination">
          <li class="page-item"><a href="#" class="page-link">이전</a></li>
          <li class="page-item"><a href="#" class="page-link">1</a></li>
          <li class="page-item active" aria-current="page">
            <span class="page-link">2</span>
          </li>
          <li class="page-item"><a href="#" class="page-link">3</a></li>
          <li class="page-item"><a href="#" class="page-link">다음</a></li>
        </ul>
      </nav>
    </div>

    <br /><br />

    <div class="search-section">
      <h2 class="d-inline search">상품 검색</h2>
      <input
        class="searchBar"
        type="text"
        placeholder="검색어를 입력해주세요"
      />
      <button class="searchBtn" type="submit">검색</button>
    </div>

    <br /><br />

    <div class="search-results">
      <h2 class="search-results-header">검색 결과: "단어"</h2>
      <div class="itemBoxDiv row g-3 gap-3">
        <div class="itemBox col"><div class="p-3">상품 A</div></div>
        <div class="itemBox col"><div class="p-3">상품 B</div></div>
        <div class="itemBox col"><div class="p-3">상품 C</div></div>
      </div>
      <nav aria-label="Page navigation example">
        <ul class="pagination">
          <li class="page-item"><a href="#" class="page-link">이전</a></li>
          <li class="page-item"><a href="#" class="page-link">1</a></li>
          <li class="page-item active" aria-current="page">
            <span class="page-link">2</span>
          </li>
          <li class="page-item"><a href="#" class="page-link">다음</a></li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<style scoped>
.itemBox {
  border: 1px solid rgba(231, 236, 243, 1);
  border-radius: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  width: 164px; /* 상품 박스 너비 */
  height: 164px; /* 상품 박스 높이 */
}

.searchBar {
  width: 50%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  padding: 20px;
}

.searchBtn {
  width: 80px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 30px;
  background-color: rgba(68, 140, 116, 1);
  margin: 1%;
}

.searchBtn:active {
  background-color: lightgrey;
  color: black;
}

.pagination .page-link {
  color: rgba(68, 140, 116, 1);
}

.pagination .active > .page-link {
  background-color: rgba(68, 140, 116, 1);
  border: none;
}

.pagination .page-link:hover {
  color: white;
}

.pagination {
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color: rgba(68, 140, 116, 1);
}
</style>
