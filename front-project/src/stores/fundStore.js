import { defineStore } from 'pinia';

export const useFundStore = defineStore('fundStore', {
    state: () => ({
        fundDetails: {}, // 펀드 정보 저장소
    }),
    actions: {
        setFundDetails(details) {
            this.fundDetails = details; // 펀드 정보를 상태로 저장
        },
    },
});