import { nowMainPageState } from "@/atoms/common.atom";
import { userInfoState } from "@/atoms/user.atom";
import { Broadcast } from "@/components/broadcast/Broadcast";
import { Header } from "@/components/header/Header";
import { MypageNav } from "@/components/sideNav/mypageNav/MypageNav";
import { useEffect } from "react";
import { Outlet } from "react-router-dom";
import { useRecoilValue, useSetRecoilState } from "recoil";
import style from "./Mypage.module.css";

export const Mypage = () => {
  const userInfo = useRecoilValue(userInfoState);
  const setNowMainPage = useSetRecoilState(nowMainPageState);

  const memberTitle = "마이페이지";
  const adminTitle = "관리자페이지";
  const memberContent = [
    "나의 정보 관리",
    "알림 / 공지사항",
    "인벤토리",
    "로그아웃",
  ];
  const adminContent = ["공지사항", "유저관리", "로그아웃"];
  const memberUrls = ["myinfo", "notice", "inventory", ""];
  const adminUrls = ["notice-manage", "user-manage", ""];

  useEffect(() => {
    setNowMainPage(false);
  }, []);

  return (
    <>
      <Header />
      <div>
        <Broadcast />
      </div>
      <div>
        <div className={style.mypage}>
          <div className={style.leftTab}>
            <MypageNav
              sideNavContent={
                userInfo.userRole === "ROLE_ADMIN"
                  ? adminContent
                  : memberContent
              }
              sideNavTitle={
                userInfo.userRole === "ROLE_ADMIN" ? adminTitle : memberTitle
              }
              urls={userInfo.userRole === "ROLE_ADMIN" ? adminUrls : memberUrls}
            />
          </div>
          <div className={style.rightTab}>
            <div className={style.content}>
              <Outlet />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};