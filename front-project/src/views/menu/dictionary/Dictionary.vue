<template>
  <div class="bc">
    <div class="container text-center">
      <h1>용어 사전</h1>
      <br /><br />
      <div class="dic">
        <br />
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
                  <button type="button" class="searchBtn" @click="filterTerms">
                    검색
                  </button>
                </div>
              </td>
            </tr>
            <tr class="firstFilter">
              <td style="width: 15%">
                <h4>한글 순</h4>
              </td>
              <td>
                <button
                  v-for="hangul in hangulList"
                  :key="hangul"
                  :class="{ activeBtn: selectedHangul === hangul }"
                  class="hangul"
                  @click="filterTermsByHangul(hangul)"
                >
                  {{ hangul }}
                </button>
              </td>
            </tr>
            <tr class="firstFilter">
              <td>
                <h4>알파벳 순</h4>
              </td>
              <td>
                <button
                  v-for="alphabet in alphabetList"
                  :key="alphabet"
                  :class="{ activeBtn: selectedAlphabet === alphabet }"
                  class="hangul"
                  @click="filterTermsByAlphabet(alphabet)"
                >
                  {{ alphabet }}
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <br />
      </div>
      <br />
      <h5 class="text-end" style="width: 93%">
        <i class="ai-search"></i>"{{ searchTerm }}{{ selectedHangul
        }}{{ selectedAlphabet }}" 검색 결과 "{{ filteredTerms.length }}"건의
        정보가 검색되었습니다.
      </h5>
      <div class="row">
        <div class="p-2 col-4 scrollbar">
          <ul class="text-start dicSubject">
            <li v-if="filteredTerms.length === 0">
              <h3>검색된 검색어가 없습니다.</h3>
            </li>
            <li
              v-for="(term, index) in filteredTerms"
              :key="index"
              @click="selectTerm(term)"
              :class="{ active: selectedTerm === term }"
            >
              <h3>{{ term.termName }}</h3>
            </li>
          </ul>
        </div>
        <div class="p-2 col-7 text-start">
          <span v-if="filteredTerms.length === 0">
            <h3 class="test-center m-5">검색된 검색어가 없습니다.</h3>
          </span>
          <h2 v-if="selectedTerm">{{ selectedTerm.termName }}</h2>
          <p v-if="selectedTerm">{{ selectedTerm.termDescription }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      terms: [],
      filteredTerms: [],
      selectedTerm: null,
      searchTerm: '',
      selectedHangul: null,
      selectedAlphabet: null,
      error: null,
      loading: true,
      hangulList: [
        'ㄱ',
        'ㄴ',
        'ㄷ',
        'ㄹ',
        'ㅁ',
        'ㅂ',
        'ㅅ',
        'ㅇ',
        'ㅈ',
        'ㅊ',
        'ㅋ',
        'ㅌ',
        'ㅍ',
        'ㅎ',
      ],
      alphabetList: [
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J',
        'K',
        'L',
        'M',
        'N',
        'O',
        'P',
        'Q',
        'R',
        'S',
        'T',
        'U',
        'V',
        'W',
        'X',
        'Y',
        'Z',
      ],
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
        this.terms = response.data;
        this.filteredTerms = response.data;

        // 용어가 존재하면 첫 번째 용어를 기본 선택
        if (this.filteredTerms.length > 0) {
          this.selectedTerm = this.filteredTerms[0];
        }
      } catch (err) {
        this.error = err;
      } finally {
        this.loading = false;
      }
    },
    // 초성 추출 함수
    getInitialConsonant(char) {
      const initialConsonants = [
        'ㄱ',
        'ㄲ',
        'ㄴ',
        'ㄷ',
        'ㄸ',
        'ㄹ',
        'ㅁ',
        'ㅂ',
        'ㅃ',
        'ㅅ',
        'ㅆ',
        'ㅇ',
        'ㅈ',
        'ㅉ',
        'ㅊ',
        'ㅋ',
        'ㅌ',
        'ㅍ',
        'ㅎ',
      ];

      const code = char.charCodeAt(0) - 44032; // 한글 유니코드 시작점
      if (code >= 0 && code <= 11171) {
        const initialIndex = Math.floor(code / 588); // 초성 추출
        return initialConsonants[initialIndex];
      }
      return char; // 한글이 아니면 그대로 반환
    },
    // 한글 자음으로 필터링
    filterTermsByHangul(hangul) {
      this.selectedHangul = hangul;
      this.selectedAlphabet = null;
      this.filteredTerms = this.terms.filter(
        (term) => this.getInitialConsonant(term.termName[0]) === hangul
      );
      this.selectedTerm =
        this.filteredTerms.length > 0 ? this.filteredTerms[0] : null;
    },
    // 알파벳으로 필터링
    filterTermsByAlphabet(alphabet) {
      this.selectedAlphabet = alphabet;
      this.selectedHangul = null;
      this.filteredTerms = this.terms.filter(
        (term) => term.termName[0].toUpperCase() === alphabet
      );
      this.selectedTerm =
        this.filteredTerms.length > 0 ? this.filteredTerms[0] : null;
    },
    filterTerms() {
      const search = this.searchTerm.toLowerCase();
      this.filteredTerms = this.terms.filter((term) =>
        term.termName.toLowerCase().includes(search)
      );
      this.selectedTerm =
        this.filteredTerms.length > 0 ? this.filteredTerms[0] : null;
    },
    selectTerm(term) {
      this.selectedTerm = term;
    },
  },
};
</script>

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

.dic {
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-left: 30px;
  padding: 20px;
  width: 91%;
}

.row {
  width: 99%;
  height: 450px;
  padding: 10px;
  margin: 20px;
}

.p-2.col-4 {
  background-color: #f7f9fc;
  border: 1px solid lightgrey;
  border-radius: 30px;
  margin-right: 15px;
}

.p-2.col-7 {
  border: 1px solid lightgrey;
  border-radius: 30px;
}

.searchBtn {
  display: inline-block;
  width: 80px;
  height: 40px;
  color: white;
  border: none;
  border-radius: 20px;
  background-color: rgba(68, 140, 116, 1);
}

.searchBtn:active {
  background-color: lightgrey;
  color: black;
}

table {
  margin-left: 8%;
}

.ai-search {
  margin: 15px;
}

.searchBar {
  display: flex;
  width: 80%;
  height: 50px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 30px;
  align-items: center;
  margin-bottom: 20px;
}

h4 {
  margin-right: 2%;
}

.search {
  border: none;
  width: 89%;
  height: 40px;
  margin: 20px;
}

.hangul {
  width: 50px;
  height: 50px;
  margin: 6px;
  border: 1px solid rgba(215, 221, 227, 1);
  border-radius: 10px;
}

.activeBtn {
  background-color: rgba(68, 140, 116, 1);
  color: white;
}

template {
  margin-right: -20%;
}
</style>
