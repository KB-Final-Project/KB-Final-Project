<script>
import axios from 'axios';

export default {
  methods: {
    async fetchTerms() {
      try {
        const response = await axios.get('http://localhost:8081/api/terms/getTerms'); // API URL
        console.log(response);
        this.terms = response.data;
      } catch (err) {
        this.error = err;
      } finally {
        this.loading = false;
      }
    },
  }
}
</script>

<template>
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
              <i class="ai-search"></i>
              <input class="search" placeholder="키워드를 입력해주세요">
              <button type="button" class="searchBtn">검색</button>
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
    <h5 class="text-end" style="width: 93%;"><i class="ai-search"></i>"ㄱ" 검색 결과 "152"건의 정보가 검색되었습니다.</h5>
    <br><br>
    <div class="row">
      <div class="p-2 col-4">
        검색결과
      </div>
      <div class="p-2 col-7">
        검색내용
      </div>
    </div>
  </div>


  <div>
    <button @click="fetchTerms">Terms</button>
    <ul v-if="!loading">
      <li v-for="(term, index) in terms" :key="index">{{ term.name }}</li> <!-- term.name을 필요한 필드로 바꿔주세요 -->
    </ul>
    <p v-else>Loading...</p>
    <p v-if="error">Error: {{ error.message }}</p>
  </div>

</template>

<style scoped>

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
  height: 40px;
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

</style>