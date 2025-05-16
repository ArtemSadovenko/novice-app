import React, { useEffect } from "react";
import { useAuth } from "../context/AuthContext";
import SidePannel from "../components/SidePannel";
import { Grid } from "@mui/material";
import HeaderPannel from "../components/HeaderPannel";
import withDashboardLayout from "../components/withDashboardLayout";

function Dashboard() {
  return (
    <>
      Dash
    </>
  );
}

export default withDashboardLayout( Dashboard);
