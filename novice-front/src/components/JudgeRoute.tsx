import React, { useEffect, useState } from "react";
import { Route, Redirect, RouteProps } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { RoomDetails } from "../types/roomdetails";
import { getJudgeAllRoomDetails } from "../api/roomApi";

interface JudgeRouteProps extends RouteProps {
  redirectPath?: string;
}

const JudgeRoute: React.FC<JudgeRouteProps> = ({
  redirectPath = "/dashboard",
  ...routeProps
}) => {
 const [isJudge, setIsJudje] = useState(false)
    const [rooms, setRooms] = useState<RoomDetails[]>()
    const [loading, setLoading] = useState(true)


    useEffect(() => {
        const fetchJudgeRooms = async () => {
            const data = await getJudgeAllRoomDetails()
            if (data) {
                setRooms(data)
                if (data.length != 0) {
                    setIsJudje(true)
                }
                setLoading(false)
            }
        }
        fetchJudgeRooms()
    }, [])

  if (loading) {

    return <div>Loading...</div>;
  }
  
  if (!isJudge) {
    return <Redirect to={redirectPath} />;
  }

  return <Route {...routeProps} />;
};

export default JudgeRoute;