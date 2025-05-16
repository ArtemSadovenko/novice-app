import React, { useEffect, useState } from 'react'
import withDashboardLayout from '../components/withDashboardLayout'
import { UserPreview } from '../types/auth'
import { getAllUsersPreview } from '../api/userApi'
import { Box } from '@mui/material'
import ToplistUserPreview from '../components/ToplistUserPreview'

function TopPage() {

  const [loading, setLoading] = useState(true)
  const [users, setUsers] = useState<UserPreview[]>([])
  useEffect(() => {
    const fetchUsers = async () => {
      const data: UserPreview[] = await getAllUsersPreview();
      data.sort((a, b) => Number.parseFloat(b.averageScore) - Number.parseFloat(a.averageScore))
      setUsers(data);
    };

    fetchUsers();
    if (users) {
      setLoading(false)
    }
  }, []);


  return (
    <Box sx={{
      height: "100%",
      width: "100%",

      padding: "20px 10px 20px 10px "
    }}>
      {loading
        ? (<div>loading....</div>)
        : (
          <>
            {users.map((user, index) => (
              <ToplistUserPreview key={user.id} ordinal={index + 1} user={user} />
            ))}
          </>)}
    </Box>
  )
}

export default withDashboardLayout(TopPage)
