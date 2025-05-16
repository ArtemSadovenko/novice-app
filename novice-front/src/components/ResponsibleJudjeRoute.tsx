import React, { useEffect, useState } from "react";
import { Route, Redirect, RouteProps, useParams } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { RoomDetails } from "../types/roomdetails";
import { getJudgeAllRoomDetails, getRoomDetailsById } from "../api/roomApi";

interface ResponsibleJudgeRouteProps extends RouteProps {
    redirectPath?: string;
}

const ResponsibleJudgeRoute: React.FC<ResponsibleJudgeRouteProps> = ({
    redirectPath = "/dashboard",
    ...routeProps
}) => {
    const { currentUser } = useAuth()
    const { id } = useParams<{ id: string }>();
    const [isJudge, setIsJudje] = useState(false)
    const [rooms, setRooms] = useState<RoomDetails>()
    const [loading, setLoading] = useState(true)


    useEffect(() => {
        const fetchJudgeRooms = async () => {
            const data = await getRoomDetailsById(id)
            if (data && currentUser) {
                setRooms(data)
                if (data.judges.map(e => e.id).includes(currentUser.id)) {
                    setIsJudje(true)
                }
                setLoading(false)
            }
        }
        fetchJudgeRooms()
    }, [currentUser])

    if (loading) {

        return <div>Loading...</div>;
    }

    if (!isJudge) {
        return <Redirect to={redirectPath} />;
    }

    return <Route {...routeProps} />;
};

export default ResponsibleJudgeRoute;