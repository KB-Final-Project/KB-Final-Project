<!-- <template>
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
      <h2 style="margin-left: 20px">ISA 계좌란?</h2>
      <p class="description" style="margin-left: 20px">
        하나의 계좌로 다양한 금융상품을 관리하면서, 절세혜택도 누릴 수 있는
        "통합계좌"입니다.<br /><br />
        채권, 국내상장주식, 펀드, 리츠, ELS, RP 등 다양한 상품을 한 계좌에 담아
        본인의 투자 전략에 맞게 운용하면서 최대 400만원까지 비과세, 초과분은
        9.9% 분리과세 적용을 받을 수 있습니다.
      </p>
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
        height: 480px;
      "
    >
      <h2 style="margin-left: 20px; text-align: left">
        ㅇㅇㅇ성향 고객님들이 선택한 BEST 인기상품
      </h2>
      <h4 style="margin-left: 20px; text-align: left">
        가장 많이 사랑 받은 예금 상품
      </h4>
      <div
        class="itemBoxDiv row g-3 gap-3"
        style="display: flex; flex-wrap: wrap; justify-content: flex-start"
      >

        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 1</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 2</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 3</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 4</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 5</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 6</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 7</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 8</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 164px; height: 150px"
        >
          <div class="p-3">상품 9</div>
        </div>
      </div>
      <nav aria-label="Page navigation example">
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
  display: inline-block;
  width: 164px; /* 상품 박스 넓이 */
  height: 150px; /* 상품 박스 높이 */
  background-color: white;
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

 -->

<!-- <template>
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
      <h2 style="margin-left: 20px">ISA 계좌란?</h2>
      <p class="description" style="margin-left: 20px">
        하나의 계좌로 다양한 금융상품을 관리하면서, 절세혜택도 누릴 수 있는
        "통합계좌"입니다.<br /><br />
        채권, 국내상장주식, 펀드, 리츠, ELS, RP 등 다양한 상품을 한 계좌에 담아
        본인의 투자 전략에 맞게 운용하면서 최대 400만원까지 비과세, 초과분은
        9.9% 분리과세 적용을 받을 수 있습니다.
      </p>
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
        height: 600px;
      "
    >
      <h2 style="margin-left: 20px; text-align: left">
        ㅇㅇㅇ성향 고객님들이 선택한 BEST 인기상품
      </h2>
      <h4 style="margin-left: 20px; text-align: left">
        가장 많이 사랑 받은 예금 상품
      </h4>
      <div
        class="itemBoxDiv"
        style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px"
      >
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 1</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 2</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 3</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 4</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 5</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 6</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 7</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 8</div>
        </div>
        <div
          class="itemBox"
          style="border-radius: 30px; width: 150px; height: 150px"
        >
          <div class="p-3">상품 9</div>
        </div>
      </div>
      <nav aria-label="Page navigation example">
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
</style> -->

<!-- <template>
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
      <h2 style="margin-left: 20px">ISA 계좌란?</h2>
      <p class="description" style="margin-left: 20px">
        하나의 계좌로 다양한 금융상품을 관리하면서, 절세혜택도 누릴 수 있는
        "통합계좌"입니다.<br /><br />
        채권, 국내상장주식, 펀드, 리츠, ELS, RP 등 다양한 상품을 한 계좌에 담아
        본인의 투자 전략에 맞게 운용하면서 최대 400만원까지 비과세, 초과분은
        9.9% 분리과세 적용을 받을 수 있습니다.
      </p>
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
        height: 600px;
      "
    >
      <h2 style="margin-left: 20px; text-align: left">
        ㅇㅇㅇ성향 고객님들이 선택한 BEST 인기상품
      </h2>
      <h4 style="margin-left: 20px; text-align: left">
        가장 많이 사랑 받은 예금 상품
      </h4>
      <div
        class="itemBoxDiv"
        style="
          display: grid;
          grid-template-columns: repeat(3, 1fr);
          gap: 20px;
          justify-items: center;
        "

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
      <nav aria-label="Page navigation example">
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
</style> -->

<template>
  <div class="container text-center">
    <h1 class="d-inline">ISA</h1>
    <p class="d-inline">ISA 하나로 모든 투자를 해결</p>
    <br /><br />

    <!-- ISA 계좌 설명 박스 -->
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
      <h2 style="margin-left: 20px">ISA 계좌란?</h2>
      <p class="description" style="margin-left: 20px">
        하나의 계좌로 다양한 금융상품을 관리하면서, 절세혜택도 누릴 수 있는
        "통합계좌"입니다.<br /><br />
        채권, 국내상장주식, 펀드, 리츠, ELS, RP 등 다양한 상품을 한 계좌에 담아
        본인의 투자 전략에 맞게 운용하면서 최대 400만원까지 비과세, 초과분은
        9.9% 분리과세 적용을 받을 수 있습니다.
      </p>
    </div>

    <br /><br />

    <!-- 상품 박스 배경 -->
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
        height: 700px;
      "
    >
      <h2 style="margin-left: 20px; text-align: left">
        AROUND가 김수대 고객님에게 추천한 ISA
      </h2>
      <h4 style="margin-left: 20px; text-align: left">
        가장 많이 사랑 받은 예금 상품
      </h4>
      <div
        class="itemBoxDiv"
        style="
          display: grid;
          grid-template-columns: repeat(3, 1fr);
          gap: 20px;
          justify-items: center;
        "
      >
        <!-- 상품 박스 9개 -->
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
      <nav aria-label="Page navigation example">
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
