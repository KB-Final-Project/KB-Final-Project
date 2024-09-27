<script>
import axios from 'axios';

export default {
  data() {
    return {
      terms: [],
      filteredTerms: [],
      selectedTerm: null,
      searchTerm: '',
      error: null,
      loading: true,
    };
  },
  mounted() {
    this.fetchTerms();
  },
  methods: {
    async fetchTerms() {
      this.loading = true;
      try {
        const response = await axios.get('/api/terms/getTerms');
        console.log('API 호출 성공:', response.data);
        this.terms = response.data;
        this.filteredTerms = response.data;
      } catch (err) {
        this.error = err;
        console.error('데이터를 가져오는 중 오류 발생:', err);
      } finally {
        this.loading = false;
      }
    },
    filterTerms() {
      const search = this.searchTerm.toLowerCase(); // 검색어 소문자로 변환
      this.filteredTerms = this.terms.filter(term =>
          term.termName.toLowerCase().includes(search) // 검색어가 포함된 용어 필터링
      );

      // 첫 번째 검색 결과를 선택된 용어로 설정
      this.selectedTerm = this.filteredTerms.length > 0 ? this.filteredTerms[0] : null;
    },
    selectTerm(term) {
      this.selectedTerm = term;
    },
  },
};
</script>

<template>
<div class="bc">
  <div class="container text-center">
    <h1>용어 사전</h1><br><br>
    <div class="dic">
      <br>
      <table class="text-start">
        <tbody>
        <tr class="firstFilter">
          <td>
            <h4>금융용어</h4>
          </td>
          <td>
            <div class="searchBar">
              <input
                  class="search"
                  placeholder="키워드를 입력해주세요"
                  v-model="searchTerm"
                  @input="filterTerms"
              />
              <button type="button" class="searchBtn" @click="filterTerms">검색</button>
            </div>
          </td>
        </tr>
        <tr class="firstFilter">
          <td>
            <h4>한글 순</h4>
          </td>
          <td>
            <button class="hangul">ㄱ</button>
            <button class="hangul">ㄴ</button>
            <button class="hangul">ㄷ</button>
            <button class="hangul">ㄹ</button>
            <button class="hangul">ㅁ</button>
            <button class="hangul">ㅂ</button>
            <button class="hangul">ㅅ</button>
            <button class="hangul">ㅇ</button>
            <button class="hangul">ㅈ</button>
            <button class="hangul">ㅊ</button>
            <button class="hangul">ㅋ</button>
            <button class="hangul">ㅌ</button>
            <button class="hangul">ㅍ</button>
            <button class="hangul">ㅎ</button>
          </td>
        </tr>
        <tr class="firstFilter">
          <td>
            <h4>알파벳 순</h4>
          </td>
          <td>
            <button class="hangul">A</button>
            <button class="hangul">B</button>
            <button class="hangul">C</button>
            <button class="hangul">D</button>
            <button class="hangul">E</button>
            <button class="hangul">F</button>
            <button class="hangul">G</button>
            <button class="hangul">H</button>
            <button class="hangul">I</button>
            <button class="hangul">J</button>
            <button class="hangul">K</button>
            <button class="hangul">L</button>
            <button class="hangul">M</button>
            <button class="hangul">N</button>
          </td>
        </tr>
        <tr class="firstFilter">
          <td></td>
          <td>
            <button class="hangul">O</button>
            <button class="hangul">P</button>
            <button class="hangul">Q</button>
            <button class="hangul">R</button>
            <button class="hangul">S</button>
            <button class="hangul">T</button>
            <button class="hangul">U</button>
            <button class="hangul">V</button>
            <button class="hangul">W</button>
            <button class="hangul">X</button>
            <button class="hangul">Y</button>
            <button class="hangul">Z</button>
          </td>
        </tr>
        </tbody>
      </table>
      <br>
    </div>
    <br>
    <h5 class="text-end" style="width: 93%;"><i class="ai-search"></i>"{{ searchTerm }}" 검색 결과 "{{ filteredTerms.length }}"건의 정보가 검색되었습니다.</h5>
    <br><br>
    <div class="row">
      <div class="p-2 col-4 scrollbar">
        <ul class="text-start dicSubject">
          <li v-for="(term, index) in filteredTerms"
              :key="index"
              @click="selectTerm(term)"
              :class="{ 'active': selectedTerm === term }">
            <h3>{{ term.termName }}</h3>
          </li>
        </ul>
      </div>
      <div class="p-2 col-7 text-start">
        <h2 v-if="selectedTerm">{{ selectedTerm.termName }}</h2>
        <p v-if="selectedTerm">{{ selectedTerm.termDescription }}</p>
      </div>
    </div>
  </div>
</div>



</template>

<style scoped>
.active {
  text-decoration: underline;
}
.scrollbar {
  overflow-y: scroll;
}
.scrollbar::-webkit-scrollbar-corner {
  background: transparent;
}

.dic{
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-left : 30px;
  width: 91%;
}

.row{
  width: 99%;
  height: 450px;
  padding: 10px;
  margin : 20px;
}

.p-2.col-4{
  background-color: #F7F9FC;
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-right: 15px;
}

.p-2.col-7{
  border: 1px solid lightgrey;
  border-radius: 30px;
}

.searchBtn{
  display: inline;
  width: 80px;
  height: 40px;
  color: white;
  border: none;
  border-radius: 20px;
  background-color: rgba(68, 140, 116, 1);
}

.searchBtn:active{
  background-color: lightgrey;
  color: black;
}

table{
  margin-left: 7%;
}

.ai-search{
  margin: 15px;
}

.searchBar{
  display:flex;
  width: 100%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  align-items: center;
}

h4{
  margin: 30px;
}
.search{
  border: none;
  width: 100%;
  height: 30px;
  margin: 10px;
}

.hangul{
  width: 50px;
  height: 50px;
  margin: 6px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 10px;
}

.hangul:active{
  background-color: rgba(68, 140, 116, 1);
  color: white;
}

template{
  margin-right: -20%;
}

.container{
  padding-top: 80px;
}

.dicSubject{
  height: 380px;
  margin-right: 15px;
  list-style: none;
  padding: 20px;
}

.dicSubject h3 {
  margin: 20px;
  cursor: pointer;
}

.dicSubject li.active h3 {
  text-decoration: underline;
  color: rgba(68, 140, 116, 1);
}

.col-7 h2{
  color: rgba(68, 140, 116, 1);
  margin: 20px;
}
.col-7 p{
  font-size: 20px;
  margin: 20px;
}
</style>