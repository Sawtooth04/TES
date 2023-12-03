import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login/Login";
import Registration from "./components/Registration/Registration";
import PrivateRoute from "./components/PrivateRoute/PrivateRoute";
import CustomerVerification from "./components/CustomerVerification/CustomerVerification";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/verification/:token" element={<CustomerVerification/>}/>
              <Route path="/login" element={<Login/>}/>
              <Route path="/registration" element={<Registration/>}/>
              <Route path="*" element={<PrivateRoute/>}/>
          </Routes>
      </Router>
  );
}

export default App;
