package com.example.jakartarestjwt;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;

// starting point
@ApplicationPath(value = "")
// declare is important
@DeclareRoles(value = { "USER", "ADMIN" })
public class Application extends jakarta.ws.rs.core.Application
{
}